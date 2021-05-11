package net.hycrafthd.event_system.events;

import java.util.UUID;

import net.hycrafthd.event_system.Event;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.multiplayer.PlayerInfo;

public class ServerChatMessageEvent extends RawServerChatMessageEvent {
	
	public ServerChatMessageEvent(String message, UUID uuid, PlayerInfo playerInfo) {
		super(message, uuid, playerInfo);
	}
}
