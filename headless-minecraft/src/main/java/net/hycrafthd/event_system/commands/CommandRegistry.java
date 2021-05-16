package net.hycrafthd.event_system.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;

public class CommandRegistry {
	
	private static HashMap<String, CommandAction> commands = new HashMap<>();
	
	private static HashMap<String, CommandAction> restrictedCommands = new HashMap<>();
	
	private static final ArrayList<UUID> allowedPlayers = new ArrayList<>();
	
	private static HashMap<String, ArrayList<String>> aliasForCommand = new HashMap<String, ArrayList<String>>();
	
	static {
		allowedPlayers.add(UUID.fromString("1e26ade8-8288-4963-87a5-b478f08c0633"));
		registerInfo();
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
		aliasForCommand.put(command, new ArrayList<String>());
	}
	
	public static void registerAlias(String command, String... alias) throws CommandRegisterException {
		if (!commands.containsKey(command) && !restrictedCommands.containsKey(command)) {
			throw new CommandRegisterException("Command " + command + " not found");
		}
		
		if (commands.containsKey(command)) {
			for (String s : alias) {
				if (!commands.containsKey(s)) {
					commands.put(s, commands.get(command));
					
					if (aliasForCommand.containsKey(command)) {
						aliasForCommand.get(command).add(s);
					} else {
						ArrayList<String> temp = new ArrayList<String>();
						temp.add(s);
						aliasForCommand.put(command, temp);
					}
					
				} else {
					throw new CommandRegisterException("Alias: " + s + " is already registered!");
				}
			}
		}
		
		if (restrictedCommands.containsKey(command)) {
			for (String s : alias) {
				if (!restrictedCommands.containsKey(s)) {
					restrictedCommands.put(s, restrictedCommands.get(command));
					
					if (aliasForCommand.containsKey(command)) {
						aliasForCommand.get(command).add(s);
					} else {
						ArrayList<String> temp = new ArrayList<String>();
						temp.add(s);
						aliasForCommand.put(command, temp);
					}
					
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
	
	private static void registerInfo() {
		try {
			registerCommand("Info", true, (command, args) -> {
				chat("List of all Commands:");
				aliasForCommand.entrySet().forEach(entry -> {
					chat("----------------------------------------");
					if (entry.getValue().isEmpty()) {
						chat("Command: " + entry.getKey() + " without aliasse");
					} else {
						chat("Command: " + entry.getKey() + " with aliasse: " + String.join(", ", entry.getValue()));
					}
				});
			});
		} catch (CommandRegisterException e) {
		}
	}
	
	private static void chat(String out) {
		System.out.println(Thread.currentThread());
		HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat(out);
	}
	
	public static class CommandRegisterException extends Exception {
		
		public CommandRegisterException(String message) {
			super(message);
		}
	}
}
