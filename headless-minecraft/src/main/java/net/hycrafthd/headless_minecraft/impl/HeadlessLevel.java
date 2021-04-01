package net.hycrafthd.headless_minecraft.impl;

import java.util.function.Supplier;

import net.hycrafthd.headless_minecraft.network.ClientListener;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class HeadlessLevel extends ClientLevel {
	
	private final ClientListener clientListener;
	
	public HeadlessLevel(ClientListener clientListener, ClientLevelData levelData, ResourceKey<Level> dimension, DimensionType dimensionType, int viewDistance, Supplier<ProfilerFiller> profiler, boolean isDebug, long seed) {
		super(null, levelData, dimension, dimensionType, viewDistance, profiler, null, isDebug, seed);
		this.clientListener = clientListener;
	}
	
}
