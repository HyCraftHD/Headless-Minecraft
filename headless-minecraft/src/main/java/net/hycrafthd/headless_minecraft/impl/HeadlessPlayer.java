package net.hycrafthd.headless_minecraft.impl;

import net.hycrafthd.headless_minecraft.network.ClientListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.stats.StatsCounter;

public class HeadlessPlayer extends LocalPlayer {
	
	private final ClientListener clientListener;
	
	public HeadlessPlayer(ClientListener clientListener, HeadlessLevel level, StatsCounter stats, ClientRecipeBook recipeBook, boolean wasShiftKeyDown, boolean wasSprinting) {
		super(null, level, null, stats, recipeBook, wasShiftKeyDown, wasSprinting);
		this.clientListener = clientListener;
	}
	
}
