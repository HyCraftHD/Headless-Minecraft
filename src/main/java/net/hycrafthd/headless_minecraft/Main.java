package net.hycrafthd.headless_minecraft;

import net.hycrafthd.headless_minecraft.event_system.EventHandler;
import net.hycrafthd.headless_minecraft.event_system.EventManager;
import net.hycrafthd.headless_minecraft.event_system.commands.CommandExecution;
import net.hycrafthd.headless_minecraft.event_system.commands.CommandRegistry;
import net.hycrafthd.headless_minecraft.event_system.events.ChatTellEvent;
import net.hycrafthd.headless_minecraft.event_system.events.RawServerChatMessageEvent;

public class Main {

	public static void main(String[] args) {
		net.minecraft.client.main.Main.LOGGER.info("HELLO from main");
		
		new Main();
		
		net.minecraft.client.main.Main.main(args);
	}

	public Main() {
		EventManager.registerListener(this);
		EventManager.registerListener(new CommandExecution());
		CommandRegistry.registerCommand("test", (command, args, player) -> { System.out.println("Command!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");}, "t");
	}
	
	@EventHandler
	public void event(RawServerChatMessageEvent event) {
		//System.out.println(event);
	}
	
	@EventHandler
	public void event2(ChatTellEvent event) {
		//System.out.println(event);
	}

}
