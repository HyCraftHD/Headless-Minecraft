package net.hycrafthd.headless_minecraft.impl;

import java.util.List;
import java.util.function.Supplier;

import net.hycrafthd.headless_minecraft.network.ClientListener;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.scores.Scoreboard;

public class HeadlessLevel extends Level {
	
	private ClientListener clientListener;
	private ClientLevelData clientLevelData;
	private HeadlessChuckCache chunkSource;
	
	public HeadlessLevel(ClientListener clientListener, ClientLevelData clientLevelData, ResourceKey<Level> resourceKey, DimensionType dimensionType, int viewDistance, Supplier<ProfilerFiller> supplier, long seed) {
		super(clientLevelData, resourceKey, dimensionType, supplier, true, false, seed);
		this.clientListener = clientListener;
		this.chunkSource = new HeadlessChuckCache(this, viewDistance);
		this.clientLevelData = clientLevelData;
		this.setDefaultSpawnPos(new BlockPos(8, 64, 8), 0.0F);
		this.updateSkyBrightness();
		this.prepareWeather();
	}
	
	public void setDefaultSpawnPos(BlockPos spawnPos, float angle) {
		this.levelData.setSpawn(spawnPos, angle);
	}
	
	@Override
	public TickList<Block> getBlockTicks() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ChunkSource getChunkSource() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TickList<Fluid> getLiquidTicks() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void levelEvent(Player arg0, int arg1, BlockPos arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public RegistryAccess registryAccess() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<? extends Player> players() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Biome getUncachedNoiseBiome(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public float getShade(Direction arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void destroyBlockProgress(int arg0, BlockPos arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Entity getEntity(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getFreeMapId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public MapItemSavedData getMapData(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RecipeManager getRecipeManager() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Scoreboard getScoreboard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TagContainer getTagManager() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void playSound(Player arg0, Entity arg1, SoundEvent arg2, SoundSource arg3, float arg4, float arg5) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playSound(Player arg0, double arg1, double arg2, double arg3, SoundEvent arg4, SoundSource arg5, float arg6, float arg7) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sendBlockUpdated(BlockPos arg0, BlockState arg1, BlockState arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setMapData(MapItemSavedData arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
