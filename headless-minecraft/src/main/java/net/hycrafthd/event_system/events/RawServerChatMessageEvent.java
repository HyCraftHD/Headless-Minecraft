package net.hycrafthd.event_system.events;

import java.util.UUID;

import net.hycrafthd.event_system.Event;
import net.minecraft.client.multiplayer.PlayerInfo;

/**
 * Called every time something is posted in the chat <br>
 * Message is the RAW message
 */
public class RawServerChatMessageEvent implements Event {
	
	protected String message;
	protected UUID uuid;
	protected PlayerInfo playerInfo;
	
	public RawServerChatMessageEvent(String message, UUID uuid, PlayerInfo playerInfo) {
		this.message = message;
		this.uuid = uuid;
		this.playerInfo = playerInfo;
	}
	
	public String getMessage() {
		return message;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	
	@Override
	public String toString() {
		return "RawServerChatMessageEvent [message=" + message + ", uuid=" + uuid + ", playerInfo=" + playerInfo + "]";
	}
}
