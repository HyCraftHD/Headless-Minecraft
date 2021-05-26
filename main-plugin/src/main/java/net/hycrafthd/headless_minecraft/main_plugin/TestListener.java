package net.hycrafthd.headless_minecraft.main_plugin;

import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.events.PlayerTickEvent;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.event_system.events.TickEvent;

public class TestListener {
	
	private int count = 0;
	
	@EventHandler
	public void testMethod(ServerChatMessageEvent event) {
		System.out.println("Normal " + event.getMessage());
	}
	
	@EventHandler
	public void testMethod(RawServerChatMessageEvent event) {
		System.out.println("Raw " + event.getMessage());
	}
	
	// @EventHandler
	// public void playerTickEvent(PlayerTickEvent.Pre event) {
	// System.out.println("Pre");
	// }
	//
	// @EventHandler
	// public void playerTickEvent(PlayerTickEvent.Post event) {
	// System.out.println("Post");
	// }
	
	@EventHandler
	public void tickEvent(TickEvent event) {
		// count++;
		// if (HeadlessMinecraft.getInstance().getConnectionManager().getPlayer() != null)
		// HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat("" + count);
	}
	
}
