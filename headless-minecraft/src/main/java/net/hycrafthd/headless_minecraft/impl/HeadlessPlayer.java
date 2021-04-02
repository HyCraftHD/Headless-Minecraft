package net.hycrafthd.headless_minecraft.impl;

import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;

public class HeadlessPlayer extends LocalPlayer {
	
	private final HeadlessPacketListener packetListener;
	
	public HeadlessPlayer(HeadlessPacketListener packetListener, HeadlessLevel level, StatsCounter stats, ClientRecipeBook recipeBook, boolean wasShiftKeyDown, boolean wasSprinting) {
		super(null, level, packetListener, stats, recipeBook, wasShiftKeyDown, wasSprinting);
		this.packetListener = packetListener;
	}
	
}
