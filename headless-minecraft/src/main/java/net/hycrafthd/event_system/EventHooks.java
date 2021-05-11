package net.hycrafthd.event_system;

import java.util.ArrayList;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.RestrictedServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.network.protocol.game.ClientboundChatPacket;

public class EventHooks {
	
	private static final ArrayList<String> allowedPlayers = new ArrayList<>();
	
	static {
		allowedPlayers.add("1e26ade8-8288-4963-87a5-b478f08c0633");
	}
	
	/**
	 * Funktioniert ist aber bs...
	 */
	public static void chatEvent(ClientboundChatPacket packet, HeadlessPacketListener listener) {
		HeadlessMinecraft minecraft = HeadlessMinecraft.getInstance();
		String message = packet.getMessage().getString();
		
		minecraft.getEventManager().executeEvents(new RawServerChatMessageEvent(message, packet.getSender(), listener.getPlayerInfo(packet.getSender())));
		
		String compare = "@" + minecraft.getConnectionManager().getPlayer().getGameProfile().getName() + " ";
		if (packet.getMessage().getString().contains(compare)) {
			message = message.substring(message.indexOf(compare) + compare.length());
			if (allowedPlayers.contains(packet.getSender().toString())) {
				minecraft.getEventManager().executeEvents(new RestrictedServerChatMessageEvent(message, packet.getSender(), listener.getPlayerInfo(packet.getSender())));
			}
		}
		minecraft.getEventManager().executeEvents(new ServerChatMessageEvent(message, packet.getSender(), listener.getPlayerInfo(packet.getSender())));
	}
}
