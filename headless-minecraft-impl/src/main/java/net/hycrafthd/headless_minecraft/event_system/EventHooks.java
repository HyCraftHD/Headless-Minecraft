package net.hycrafthd.headless_minecraft.event_system;

import net.hycrafthd.headless_minecraft.event_system.events.ChatTellEvent;
import net.hycrafthd.headless_minecraft.event_system.events.PlayerTickEvent;
import net.hycrafthd.headless_minecraft.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.headless_minecraft.event_system.events.TickEvent;
import net.minecraft.Util;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundChatPacket;

public class EventHooks {

	private EventHooks() {
	}

	/**
	 * global tick TODO
	 */
	public static void tick() {
		EventManager.executeEvents(new TickEvent());
	}

	/**
	 * playertick
	 * @param localPlayer 
	 */
	public static void preTick(LocalPlayer localPlayer) {
		EventManager.executeEvents(new PlayerTickEvent.Pre(localPlayer));
	}

	/**
	 * playertick
	 */
	public static void postTick(LocalPlayer localPlayer) {
		EventManager.executeEvents(new PlayerTickEvent.Post(localPlayer));
	}

	public static void chatEvent(ClientboundChatPacket packet, ClientPacketListener clientPacketListener) {
		String message = packet.getMessage().getString();
		PlayerInfo playerInfo = clientPacketListener.getPlayerInfo(packet.getSender());
		EventManager.executeEvents(new RawServerChatMessageEvent(message, packet.getSender(), playerInfo));
		if(playerInfo == null) {
			return;
		}
		String starts = clientPacketListener.getPlayerInfo(packet.getSender()).getProfile().getName() + " whispers to you: ";
		if(message.startsWith(starts) && !Util.NIL_UUID.equals(packet.getSender())) {
			EventManager.executeEvents(new ChatTellEvent(message.substring(starts.length()), packet.getSender(), playerInfo));
		}
		
	}
}
