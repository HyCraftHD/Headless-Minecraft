package net.hycrafthd.event_system.events;

import java.util.Arrays;
import java.util.UUID;

import net.minecraft.client.multiplayer.PlayerInfo;

public class ServerChatCommandEvent extends RawServerChatMessageEvent {
	
	private String[] args;
	
	private String command;
	
	public ServerChatCommandEvent(String message, UUID uuid, PlayerInfo playerInfo) {
		super(message, uuid, playerInfo);
		String[] t = message.split(" ");
		command = t[0];
		args = Arrays.copyOfRange(t, 1, t.length);
	}
	
	@Override
	public String toString() {
		return "ServerChatCommandEvent [message=" + command + " | args=" + Arrays.toString(args) + "]";
	}
	
	public String getCommand() {
		return command;
	}
	
	public String[] getArgs() {
		return args;
	}
}
