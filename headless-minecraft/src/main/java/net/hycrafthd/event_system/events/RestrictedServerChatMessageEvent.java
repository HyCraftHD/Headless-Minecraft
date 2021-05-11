package net.hycrafthd.event_system.events;

import java.util.UUID;

import net.minecraft.client.multiplayer.PlayerInfo;

public class RestrictedServerChatMessageEvent extends RawServerChatMessageEvent {
	
	public RestrictedServerChatMessageEvent(String message, UUID uuid, PlayerInfo playerInfo) {
		super(message, uuid, playerInfo);
	}
	
}
