package net.hycrafthd.headless_minecraft.patch_helper;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

public class HeadlessClientLevel extends ClientLevel {
	
	public HeadlessClientLevel(ClientPacketListener connection, ClientLevelData levelData, ResourceKey<Level> dimension, Holder<DimensionType> dimensionType, int viewDistance, int simulationDistance, Supplier<ProfilerFiller> profiler, LevelRenderer levelRenderer, boolean debug, long biomeZoomSeed) {
		super(connection, levelData, dimension, dimensionType, viewDistance, simulationDistance, profiler, levelRenderer, debug, biomeZoomSeed);
	}
	
	// Overwrite methods to NOP that do rendering and non headless stuff
	
	@Override
	public void animateTick(int pPosX, int pPosY, int pPosZ) {
	}
	
	@Override
	public void doAnimateTick(int pPosX, int pPosY, int pPosZ, int pRange, Random pRandom, Block pBlock, MutableBlockPos pBlockPos) {
	}
	
	@Override
	public void playSound(Player pPlayer, double pX, double pY, double pZ, SoundEvent pSound, SoundSource pCategory, float pVolume, float pPitch) {
	}
	
	@Override
	public void playSound(Player pPlayer, Entity pEntity, SoundEvent pEvent, SoundSource pCategory, float pVolume, float pPitch) {
	}
	
	@Override
	public void playLocalSound(BlockPos pPos, SoundEvent pSound, SoundSource pCategory, float pVolume, float pPitch, boolean pDistanceDelay) {
	}
	
	@Override
	public void playLocalSound(double pX, double pY, double pZ, SoundEvent pSound, SoundSource pCategory, float pVolume, float pPitch, boolean pDistanceDelay) {
	}
	
	@Override
	public void createFireworks(double pX, double pY, double pZ, double pMotionX, double pMotionY, double pMotionZ, CompoundTag pCompound) {
	}
	
	@Override
	public void sendBlockUpdated(BlockPos pPos, BlockState pOldState, BlockState pNewState, int pFlags) {
	}
	
	@Override
	public void setBlocksDirty(BlockPos pBlockPos, BlockState pOldState, BlockState pNewState) {
	}
	
	@Override
	public void setSectionDirtyWithNeighbors(int pSectionX, int pSectionY, int pSectionZ) {
	}
	
	@Override
	public void destroyBlockProgress(int pBreakerId, BlockPos pPos, int pProgress) {
	}
	
	@Override
	public void globalLevelEvent(int pId, BlockPos pPos, int pData) {
	}
	
	@Override
	public void levelEvent(Player pPlayer, int pType, BlockPos pPos, int pData) {
	}
	
	@Override
	public void addParticle(ParticleOptions pParticleData, boolean pForceAlwaysRender, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
	}
	
	@Override
	public void addParticle(ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
	}
	
	@Override
	public void addAlwaysVisibleParticle(ParticleOptions pParticleData, boolean pIgnoreRange, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
	}
	
	@Override
	public void addAlwaysVisibleParticle(ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
	}
}
