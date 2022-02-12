package net.hycrafthd.headless_minecraft.event_system.events;

import java.util.UUID;

import net.minecraft.client.multiplayer.PlayerInfo;

/**
 * Called when the message begins with <strong>@BOT_NAME </strong> <br>
 * Message is the RAW message
 */
public class ServerChatMessageEvent extends RawServerChatMessageEvent {
	
	public ServerChatMessageEvent(String message, UUID uuid, PlayerInfo playerInfo) {
		super(message, uuid, playerInfo);
	}
}
