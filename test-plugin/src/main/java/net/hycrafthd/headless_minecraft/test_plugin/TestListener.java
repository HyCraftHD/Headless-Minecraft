package net.hycrafthd.headless_minecraft.test_plugin;

import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.event_system.events.TickEvent;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;

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
	
	@EventHandler
	public void tickEvent(TickEvent event) {
		count++;
		HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat("" + count);
	}
	
}
