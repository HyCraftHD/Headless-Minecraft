package net.hycrafthd.event_system.commands;

import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;

public interface CommandAction {
	
	void action(String command, String[] args, HeadlessPlayer player);
	
}
