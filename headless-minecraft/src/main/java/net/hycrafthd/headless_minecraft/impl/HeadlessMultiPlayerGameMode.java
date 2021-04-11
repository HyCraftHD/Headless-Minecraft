package net.hycrafthd.headless_minecraft.impl;

import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;

public class HeadlessMultiPlayerGameMode extends MultiPlayerGameMode {
	
	public HeadlessMultiPlayerGameMode(HeadlessPacketListener listener) {
		super(null, listener);
	}
	
	@Override
	public LocalPlayer createPlayer(ClientLevel level, StatsCounter statsCounter, ClientRecipeBook recipeBook, boolean wasShiftKeyDown, boolean wasSprinting) {
		throw new AssertionError("Method should not be called");
	}
	
}
