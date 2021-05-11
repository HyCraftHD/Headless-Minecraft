package net.hycrafthd.headless_minecraft.test_plugin;

import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.RestrictedServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;

public class TestListener {
	
	@EventHandler
	public void testMethod(ServerChatMessageEvent event) {
		System.out.println("Normal " + event.getMessage());
	}
	
	@EventHandler
	public void testMethod(RawServerChatMessageEvent event) {
		System.out.println("Raw " + event.getMessage());
	}
	
	@EventHandler
	public void testMethod(RestrictedServerChatMessageEvent event) {
		System.out.println("Restricted " + event.getMessage());
	}
}
