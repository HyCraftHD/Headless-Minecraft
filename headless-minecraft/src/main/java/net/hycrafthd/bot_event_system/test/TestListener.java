package net.hycrafthd.bot_event_system.test;

import net.hycrafthd.bot_event_system.EventHandler;

public class TestListener {
	
	@EventHandler
	public void testMethod(TestEvent event) {
		System.out.println("TestListenerMethod");
	}
	
}
