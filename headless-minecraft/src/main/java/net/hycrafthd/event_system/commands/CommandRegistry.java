package net.hycrafthd.event_system.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

public class CommandRegistry {
	
	private static HashMap<String, CommandAction> commands = new HashMap<>();
	
	private static HashMap<String, CommandAction> restrictedCommands = new HashMap<>();
	
	private static final ArrayList<UUID> allowedPlayers = new ArrayList<>();
	
	static {
		allowedPlayers.add(UUID.fromString("1e26ade8-8288-4963-87a5-b478f08c0633"));
	}
	
	private CommandRegistry() {
	}
	
	public static void registerCommand(String command, boolean restricted, CommandAction action) throws CommandRegisterException {
		if (restricted) {
			if (restrictedCommands.containsKey(command)) {
				throw new CommandRegisterException("Command: " + command + " already registered!");
			}
			restrictedCommands.put(command, action);
		} else {
			if (commands.containsKey(command)) {
				throw new CommandRegisterException("Command: " + command + " already registered!");
			}
			commands.put(command, action);
		}
	}
	
	public static void registerAlias(String command, String... alias) throws CommandRegisterException {
		if (!commands.containsKey(command) && !restrictedCommands.containsKey(command)) {
			throw new CommandRegisterException("Command " + command + " not found");
		}
		
		if (commands.containsKey(command)) {
			for (String s : alias) {
				if (!commands.containsKey(s)) {
					commands.put(s, commands.get(command));
				} else {
					throw new CommandRegisterException("Alias: " + s + " is already registered!");
				}
			}
		}
		
		if (restrictedCommands.containsKey(command)) {
			for (String s : alias) {
				if (!restrictedCommands.containsKey(s)) {
					restrictedCommands.put(s, restrictedCommands.get(command));
				} else {
					throw new CommandRegisterException("Alias: " + s + " is already registered!");
				}
			}
		}
	}
	
	public static void executeCommand(String message, UUID sender) {
		String[] t = message.split(" ");
		String command = t[0];
		String[] args = Arrays.copyOfRange(t, 1, t.length);
		
		if (allowedPlayers.contains(sender)) {
			if (restrictedCommands.containsKey(command)) {
				restrictedCommands.get(command).action(command, args);
			}
		} else {
			if (commands.containsKey(command)) {
				commands.get(command).action(command, args);
			}
		}
	}
	
	public static class CommandRegisterException extends Exception {
		
		public CommandRegisterException(String message) {
			super(message);
		}
	}
}
