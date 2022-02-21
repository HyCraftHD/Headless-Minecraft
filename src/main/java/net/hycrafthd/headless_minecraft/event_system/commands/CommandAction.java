package net.hycrafthd.headless_minecraft.event_system.commands;

import java.util.UUID;

import net.minecraft.world.entity.player.Player;

public interface CommandAction {
	
	void action(String command, String[] args, UUID sender);
	
}
