package net.hycrafthd.bot_event_system;

import net.hycrafthd.bot_event_system.test.NotTestClass;
import net.hycrafthd.bot_event_system.test.TestEvent;
import net.hycrafthd.bot_event_system.test.TestListener;

public class Main {
	
	private EventManager manager = new EventManager();
	
	public Main() {
		manager.registerEvent(new TestListener());
		manager.executeEvents(new TestEvent());
		manager.executeEvents(new NotTestClass());
	}
	
}
