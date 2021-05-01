package net.hycrafthd.headless_minecraft.impl;

import java.util.Random;
import java.util.function.Supplier;

import net.hycrafthd.headless_minecraft.impl.handle.HeadlessLevelImpl;
import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

public class HeadlessLevel extends ClientLevel {
	
	private final HeadlessPacketListener packetListener;
	
	private HeadlessLevelImpl handle;
	
	public HeadlessLevel(HeadlessPacketListener packetListener, ClientLevelData levelData, ResourceKey<Level> dimension, DimensionType dimensionType, int viewDistance, Supplier<ProfilerFiller> profiler, boolean isDebug, long seed) {
		super(packetListener, levelData, dimension, dimensionType, viewDistance, profiler, null, isDebug, seed);
		this.packetListener = packetListener;
		handle = new HeadlessLevelImpl(this);
	}
	
	@Override
	public String toString() {
		return "HeadlessLevel";
	}
	
	@Override
	public CrashReportCategory fillReportDetails(CrashReport crashReport) {
		final CrashReportCategory category = super.fillReportDetails(crashReport);
		category.setDetail("Headless Level", () -> {
			return "Yes";
		});
		return category;
	}
	
	// Override some unused methods
	
	@Override
	public void onChunkLoaded(int x, int z) {
	}
	
	@Override
	public void clearTintCaches() {
	}
	
	@Override
	public void animateTick(int x, int y, int z) {
	}
	
	@Override
	public void doAnimateTick(int x, int y, int z, int distance, Random random, boolean barrier, MutableBlockPos pos) {
	}
	
	@Override
	public void playSound(Player player, double x, double y, double z, SoundEvent event, SoundSource source, float volume, float pitch) {
	}
	
	@Override
	public void playSound(Player player, Entity entity, SoundEvent event, SoundSource source, float volume, float pitch) {
	}
	
	@Override
	public void playLocalSound(BlockPos pos, SoundEvent event, SoundSource source, float volume, float pitch, boolean delay) {
	}
	
	@Override
	public void playLocalSound(double x, double y, double z, SoundEvent event, SoundSource source, float volume, float pitch, boolean delay) {
	}
	
	@Override
	public void createFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, CompoundTag compound) {
	}
	
	@Override
	public void sendBlockUpdated(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
	}
	
	@Override
	public void setBlocksDirty(BlockPos pos, BlockState oldState, BlockState newState) {
	}
	
	@Override
	public void setSectionDirtyWithNeighbors(int x, int y, int z) {
	}
	
	@Override
	public void destroyBlockProgress(int breaker, BlockPos pos, int progress) {
	}
	
	@Override
	public void globalLevelEvent(int type, BlockPos pos, int data) {
	}
	
	@Override
	public void levelEvent(Player player, int type, BlockPos pos, int data) {
	}
	
	@Override
	public void addParticle(ParticleOptions options, double x, double y, double z, double speedX, double speedY, double speedZ) {
	}
	
	@Override
	public void addParticle(ParticleOptions options, boolean alwaysVisible, double x, double y, double z, double speedX, double speedY, double speedZ) {
	}
	
	@Override
	public void addAlwaysVisibleParticle(ParticleOptions options, double x, double y, double z, double speedX, double speedY, double speedZ) {
	}
	
	@Override
	public void addAlwaysVisibleParticle(ParticleOptions options, boolean ignoreRange, double x, double y, double z, double speedX, double speedY, double speedZ) {
	}
	
	@Override
	public int getBlockTint(BlockPos pos, ColorResolver resolver) {
		return 0;
	}
	
	@Override
	public int calculateBlockTint(BlockPos pos, ColorResolver resolver) {
		return 0;
	}
	
	public HeadlessLevelImpl getHandle() {
		return handle;
	}
}
