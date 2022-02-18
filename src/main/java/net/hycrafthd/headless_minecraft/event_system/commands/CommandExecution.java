package net.hycrafthd.headless_minecraft.event_system.commands;

import net.hycrafthd.headless_minecraft.event_system.EventHandler;
import net.hycrafthd.headless_minecraft.event_system.events.ChatTellEvent;

public class CommandExecution {

	@EventHandler
	public void TellEvent(ChatTellEvent event) {
		CommandRegistry.executeCommand(event.getMessage(), event.getUuid());
	}
	
}
