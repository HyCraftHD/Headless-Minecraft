package net.hycrafthd.event_system.commands;

public interface CommandAction {
	
	void action(String command, String[] args);
	
}
