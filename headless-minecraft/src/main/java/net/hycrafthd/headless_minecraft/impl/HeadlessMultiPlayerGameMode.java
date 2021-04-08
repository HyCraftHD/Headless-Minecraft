package net.hycrafthd.headless_minecraft.impl;

import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;

public class HeadlessMultiPlayerGameMode extends MultiPlayerGameMode {
	
	public HeadlessMultiPlayerGameMode(HeadlessPacketListener listener) {
		super(null, listener);
	}
	
}
