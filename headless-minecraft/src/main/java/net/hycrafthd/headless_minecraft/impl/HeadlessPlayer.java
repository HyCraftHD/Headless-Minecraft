package net.hycrafthd.headless_minecraft.impl;

import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;

public class HeadlessPlayer extends LocalPlayer {
	
	private final HeadlessPacketListener clientListener;
	
	public HeadlessPlayer(HeadlessPacketListener clientListener, HeadlessLevel level, StatsCounter stats, ClientRecipeBook recipeBook, boolean wasShiftKeyDown, boolean wasSprinting) {
		super(null, level, null, stats, recipeBook, wasShiftKeyDown, wasSprinting);
		this.clientListener = clientListener;
	}
	
}
