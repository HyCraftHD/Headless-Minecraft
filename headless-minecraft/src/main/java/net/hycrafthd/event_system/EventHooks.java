package net.hycrafthd.event_system;

import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.network.protocol.game.ClientboundChatPacket;

public class EventHooks {
	
	public static void chatEvent(ClientboundChatPacket packet, HeadlessPacketListener listener) {
		HeadlessMinecraft minecraft = HeadlessMinecraft.getInstance();
		
		minecraft.getEventManager().executeEvents(new ServerChatMessageEvent(packet.getMessage().getString(), packet.getSender(), listener.getPlayerInfo(packet.getSender())));
		minecraft.getEventManager().executeEvents(new RawServerChatMessageEvent(packet.getMessage().getString(), packet.getSender(), listener.getPlayerInfo(packet.getSender())));
		
		String compare = "@" + minecraft.getConnectionManager().getPlayer().getGameProfile().getName() + " ";
		if (packet.getMessage().getString().contains(compare)) {
			// String result = message.substring(message.indexOf(compare) + compare.length());
			// this.message = result;
		}
		
	}
}
