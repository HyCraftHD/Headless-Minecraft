package net.hycrafthd.headless_minecraft.event_system.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import net.hycrafthd.headless_minecraft.event_system.CommandRegisterException;

public class CommandRegistry {

	private static HashMap<String, CommandAction> commands = new HashMap<>();

	private static final ArrayList<UUID> allowedPlayers = new ArrayList<>();

	private static HashMap<String, ArrayList<String>> aliasForCommand = new HashMap<String, ArrayList<String>>();

	static {
		allowedPlayers.add(UUID.fromString("1e26ade8-8288-4963-87a5-b478f08c0633"));
	}

	private CommandRegistry() {
	}

	public static void registerCommand(String command, CommandAction action) {
		if (commands.containsKey(command)) {
			throw new CommandRegisterException("Command: " + command + " already registered!");
		}
		commands.put(command, action);
		aliasForCommand.put(command, new ArrayList<String>());
	}

	public static void registerCommand(String command, CommandAction action, String... alias) {
		if (commands.containsKey(command)) {
			throw new CommandRegisterException("Command: " + command + " already registered!");
		}
		if (alias.length == 0) {
			throw new CommandRegisterException("Alias not found!");
		}
		commands.put(command, action);
		aliasForCommand.put(command, new ArrayList<String>(List.of(alias)));
	}

	public static void executeCommand(String message, UUID sender) {
		String[] t = message.split(" ");
		String command = t[0];
		String[] args = Arrays.copyOfRange(t, 1, t.length);
		if (commands.containsKey(command)) {
			commands.get(command).action(command, args, null); //TODO
		}
	}
}
