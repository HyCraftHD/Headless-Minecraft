package net.hycrafthd.headless_minecraft.impl;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.hycrafthd.headless_minecraft.Constants;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.chat.Component;

public class HeadlessRemotePlayer extends RemotePlayer {
	
	public HeadlessRemotePlayer(HeadlessLevel level, GameProfile profile) {
		super(level, profile);
	}
	
	@Override
	public void sendMessage(Component component, UUID uuid) {
		Constants.CHAT_LOGGER.info(component.getString());
	}
	
}
