package net.hycrafthd.headless_minecraft.event_system.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import net.hycrafthd.headless_minecraft.event_system.CommandRegisterException;

public class CommandRegistry {
	
	private static HashMap<String, CommandAction> commands = new HashMap<>();
	
	private static final ArrayList<UUID> allowedPlayers = new ArrayList<>();
	
	private static HashMap<String, String> aliasForCommand = new HashMap<String, String>();
	
	static {
		allowedPlayers.add(UUID.fromString("1e26ade8-8288-4963-87a5-b478f08c0633"));
		allowedPlayers.add(UUID.fromString("d9202ce0-f6c1-4dc1-9341-2d9091764808"));
	}
	
	private CommandRegistry() {
	}
	
	public static void registerCommand(String command, CommandAction action) {
		if (commands.containsKey(command)) {
			throw new CommandRegisterException("Command: " + command + " already registered!");
		}
		commands.put(command, action);
	}
	
	public static void registerCommand(String command, CommandAction action, String... alias) {
		if (commands.containsKey(command)) {
			throw new CommandRegisterException("Command: " + command + " already registered!");
		}
		if (alias.length == 0) {
			throw new CommandRegisterException("Alias not found!");
		}
		commands.put(command, action);
		for (String a : alias) {
			aliasForCommand.put(a, command);
		}
	}
	
	public static void executeCommand(String message, UUID sender) {
		if (!allowedPlayers.contains(sender)) {
			return;
		}
		String[] t = message.split(" ");
		String command = t[0];
		String[] args = Arrays.copyOfRange(t, 1, t.length);
		if (commands.containsKey(command)) {
			commands.get(command).action(command, args, sender); // TODO
		}
		if (aliasForCommand.containsKey(command)) {
			commands.get(aliasForCommand.get(command)).action(command, args, sender);
		}
	}
}
