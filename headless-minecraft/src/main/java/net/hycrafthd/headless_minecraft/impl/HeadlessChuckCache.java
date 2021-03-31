package net.hycrafthd.headless_minecraft.impl;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.mixin.ClientChunkCacheMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class HeadlessChuckCache extends ChunkSource {
	
	private static final Logger LOGGER = LogManager.getLogger();
	private final LevelChunk emptyChunk;
	private final LevelLightEngine lightEngine;
	private volatile HeadlessChuckCache.Storage storage;
	private final HeadlessLevel level;
	
	public HeadlessChuckCache(HeadlessLevel clientLevel, int viewDistance) {
		this.level = clientLevel;
		this.emptyChunk = new EmptyLevelChunk(clientLevel, new ChunkPos(0, 0));
		this.lightEngine = new LevelLightEngine(this, true, clientLevel.dimensionType().hasSkyLight());
		this.storage = new HeadlessChuckCache.Storage(calculateStorageRange(viewDistance));
	}
	
	public LevelLightEngine getLightEngine() {
		return this.lightEngine;
	}
	
	public void drop(int i, int j) {
		if (this.storage.inRange(i, j)) {
			int k = this.storage.getIndex(i, j);
			LevelChunk levelChunk = this.storage.getChunk(k);
			if (ClientChunkCacheMixin.isValidChunk(levelChunk, i, j)) {
				this.storage.replace(k, levelChunk, (LevelChunk) null);
			}
			
		}
	}
	
	public LevelChunk getChunk(int i, int j, ChunkStatus chunkStatus, boolean bl) {
		if (this.storage.inRange(i, j)) {
			LevelChunk levelChunk = this.storage.getChunk(this.storage.getIndex(i, j));
			if (isValidChunk(levelChunk, i, j)) {
				return levelChunk;
			}
		}
		
		return bl ? this.emptyChunk : null;
	}
	
	public BlockGetter getLevel() {
		return this.level;
	}
	
	public LevelChunk replaceWithPacketData(int i, int j, ChunkBiomeContainer chunkBiomeContainer, FriendlyByteBuf friendlyByteBuf, CompoundTag compoundTag, int k, boolean bl) {
		if (!this.storage.inRange(i, j)) {
			LOGGER.warn((String) "Ignoring chunk since it's not in the view range: {}, {}", (Object) i, (Object) j);
			return null;
		} else {
			int l = this.storage.getIndex(i, j);
			LevelChunk levelChunk = (LevelChunk) this.storage.chunks.get(l);
			if (!bl && ClientChunkCacheMixin.isValidChunk(levelChunk, i, j)) {
				levelChunk.replaceWithPacketData(chunkBiomeContainer, friendlyByteBuf, compoundTag, k);
			} else {
				if (chunkBiomeContainer == null) {
					LOGGER.warn((String) "Ignoring chunk since we don't have complete data: {}, {}", (Object) i, (Object) j);
					return null;
				}
				
				levelChunk = new LevelChunk(this.level, new ChunkPos(i, j), chunkBiomeContainer);
				levelChunk.replaceWithPacketData(chunkBiomeContainer, friendlyByteBuf, compoundTag, k);
				this.storage.replace(l, levelChunk);
			}
			
			LevelChunkSection[] levelChunkSections = levelChunk.getSections();
			LevelLightEngine levelLightEngine = this.getLightEngine();
			levelLightEngine.enableLightSources(new ChunkPos(i, j), true);
			
			for (int m = 0; m < levelChunkSections.length; ++m) {
				LevelChunkSection levelChunkSection = levelChunkSections[m];
				levelLightEngine.updateSectionStatus(SectionPos.of(i, m, j), LevelChunkSection.isEmpty(levelChunkSection));
			}
			
			this.level.onChunkLoaded(i, j);
			return levelChunk;
		}
	}
	
	public void tick(BooleanSupplier booleanSupplier) {
	}
	
	public void updateViewCenter(int i, int j) {
		this.storage.viewCenterX = i;
		this.storage.viewCenterZ = j;
	}
	
	public void updateViewRadius(int i) {
		int j = this.storage.chunkRadius;
		int k = calculateStorageRange(i);
		if (j != k) {
			HeadlessChuckCache.Storage storage = new HeadlessChuckCache.Storage(k);
			storage.viewCenterX = this.storage.viewCenterX;
			storage.viewCenterZ = this.storage.viewCenterZ;
			
			for (int l = 0; l < this.storage.chunks.length(); ++l) {
				LevelChunk levelChunk = (LevelChunk) this.storage.chunks.get(l);
				if (levelChunk != null) {
					ChunkPos chunkPos = levelChunk.getPos();
					if (storage.inRange(chunkPos.x, chunkPos.z)) {
						storage.replace(storage.getIndex(chunkPos.x, chunkPos.z), levelChunk);
					}
				}
			}
			
			this.storage = storage;
		}
		
	}
	
	private static int calculateStorageRange(int i) {
		return Math.max(2, i) + 3;
	}
	
	public String gatherStats() {
		return "Client Chunk Cache: " + this.storage.chunks.length() + ", " + this.getLoadedChunksCount();
	}
	
	public int getLoadedChunksCount() {
		return this.storage.chunkCount;
	}
	
	public void onLightUpdate(LightLayer lightLayer, SectionPos sectionPos) {
		Minecraft.getInstance().levelRenderer.setSectionDirty(sectionPos.x(), sectionPos.y(), sectionPos.z());
	}
	
	public boolean isTickingChunk(BlockPos blockPos) {
		return this.hasChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
	}
	
	public boolean isEntityTickingChunk(ChunkPos chunkPos) {
		return this.hasChunk(chunkPos.x, chunkPos.z);
	}
	
	public boolean isEntityTickingChunk(Entity entity) {
		return this.hasChunk(Mth.floor(entity.getX()) >> 4, Mth.floor(entity.getZ()) >> 4);
	}
	
	final class Storage {
		
		private final AtomicReferenceArray<LevelChunk> chunks;
		private final int chunkRadius;
		private final int viewRange;
		private volatile int viewCenterX;
		private volatile int viewCenterZ;
		private int chunkCount;
		
		private Storage(int i) {
			this.chunkRadius = i;
			this.viewRange = i * 2 + 1;
			this.chunks = new AtomicReferenceArray(this.viewRange * this.viewRange);
		}
		
		private int getIndex(int i, int j) {
			return Math.floorMod(j, this.viewRange) * this.viewRange + Math.floorMod(i, this.viewRange);
		}
		
		protected void replace(int i, LevelChunk levelChunk) {
			LevelChunk levelChunk2 = (LevelChunk) this.chunks.getAndSet(i, levelChunk);
			if (levelChunk2 != null) {
				--this.chunkCount;
				HeadlessChuckCache.this.level.unload(levelChunk2);
			}
			
			if (levelChunk != null) {
				++this.chunkCount;
			}
			
		}
		
		protected LevelChunk replace(int i, LevelChunk levelChunk, LevelChunk levelChunk2) {
			if (this.chunks.compareAndSet(i, levelChunk, levelChunk2) && levelChunk2 == null) {
				--this.chunkCount;
			}
			
			HeadlessChuckCache.this.level.unload(levelChunk);
			return levelChunk;
		}
		
		private boolean inRange(int i, int j) {
			return Math.abs(i - this.viewCenterX) <= this.chunkRadius && Math.abs(j - this.viewCenterZ) <= this.chunkRadius;
		}
		
		protected LevelChunk getChunk(int i) {
			return (LevelChunk) this.chunks.get(i);
		}
	}
}
