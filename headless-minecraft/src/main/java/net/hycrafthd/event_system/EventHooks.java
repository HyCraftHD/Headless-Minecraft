package net.hycrafthd.event_system;

import net.hycrafthd.event_system.commands.CommandRegistry;
import net.hycrafthd.event_system.events.PlayerTickEvent;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.event_system.events.TickEvent;
import net.hycrafthd.event_system.util.LeftClickManager;
import net.hycrafthd.event_system.util.PickHitResultManager;
import net.hycrafthd.event_system.util.RightClickManager;
import net.hycrafthd.event_system.util.SlowChatManager;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;
import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.network.protocol.game.ClientboundChatPacket;

public class EventHooks {
	
	private EventHooks() {
	}
	
	public static void chatEvent(ClientboundChatPacket packet, HeadlessPacketListener listener) {
		HeadlessMinecraft minecraft = HeadlessMinecraft.getInstance();
		String message = packet.getMessage().getString();
		
		minecraft.getEventManager().executeEvents(new RawServerChatMessageEvent(message, packet.getSender(), listener.getPlayerInfo(packet.getSender())));
		
		String compare = "@" + minecraft.getConnectionManager().getPlayer().getGameProfile().getName() + " ";
		if (packet.getMessage().getString().contains(compare)) {
			
			message = message.substring(message.indexOf(compare) + compare.length());
			minecraft.getEventManager().executeEvents(new ServerChatMessageEvent(message, packet.getSender(), listener.getPlayerInfo(packet.getSender())));
			CommandRegistry.executeCommand(message, packet.getSender());
		}
	}
	
	/**
	 * global tick TODO
	 */
	public static void tick() {
		HeadlessMinecraft.getInstance().getEventManager().executeEvents(new TickEvent());
		PickHitResultManager.pick();
		RightClickManager.tick();
		LeftClickManager.tick();
		SlowChatManager.tick();
	}
	
	/**
	 * playertick
	 */
	public static void preTick(HeadlessPlayer player) {
		HeadlessMinecraft.getInstance().getEventManager().executeEvents(new PlayerTickEvent.Pre(player));
	}
	
	/**
	 * playertick
	 */
	public static void postTick(HeadlessPlayer player) {
		HeadlessMinecraft.getInstance().getEventManager().executeEvents(new PlayerTickEvent.Post(player));
	}
}
