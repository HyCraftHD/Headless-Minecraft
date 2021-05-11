package net.hycrafthd.headless_minecraft.test_plugin;

import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.events.RawServerChatMessageEvent;
import net.hycrafthd.event_system.events.RestrictedServerChatMessageEvent;
import net.hycrafthd.event_system.events.ServerChatCommandEvent;
import net.hycrafthd.event_system.events.ServerChatMessageEvent;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;

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
	
	@EventHandler
	public void testMethod(ServerChatCommandEvent event) {
		System.out.println("Command " + event);
		if (event.getCommand().equalsIgnoreCase("YP") && event.getArgs() != null && event.getArgs().length == 2) {
			
			float yaw = Float.parseFloat(event.getArgs()[0]);
			float pitch = Float.parseFloat(event.getArgs()[1]);
			HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().turn(yaw, pitch);
		}
	}
}
