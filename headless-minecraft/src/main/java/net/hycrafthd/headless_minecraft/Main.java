package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixins;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft");
	
	public static void main(String[] args) throws IOException {
		Mixins.addConfiguration("mixin.json");
		
		final OptionParser parser = new OptionParser();
		
		// Default specs
		final OptionSpec<Void> helpSpec = parser.accepts("help", "Show the help menu").forHelp();
		final OptionSpec<File> runSpec = parser.accepts("run", "Run directory for headless minecraft").withRequiredArg().ofType(File.class);
		
		// User data specs
		final OptionSpec<String> authNameSpec = parser.accepts("auth-name", "Player name").withRequiredArg();
		final OptionSpec<String> authUuidSpec = parser.accepts("auth-uuid", "Player uuid").withRequiredArg();
		final OptionSpec<String> authTokenSpec = parser.accepts("auth-token", "Auth / Access token").withRequiredArg();
		final OptionSpec<String> userTypeSpec = parser.accepts("user-type", "User type").withRequiredArg();
		
		final OptionSet set = parser.parse(args);
		
		if (set.has(helpSpec) || set.specs().size() < 5) {
			parser.printHelpOn(System.out);
			return;
		}
		
		LOGGER.info("Start headless minecraft");
		
		// Get arguments
		final File run = set.valueOf(runSpec);
		
		final String authName = set.valueOf(authNameSpec);
		final String authUuid = set.valueOf(authUuidSpec);
		final String authToken = set.valueOf(authTokenSpec);
		final String userType = set.valueOf(userTypeSpec);
		
		// Validate that run, username and password are not null
		if (run == null || authName == null || authUuid == null || authToken == null || userType == null) {
			throw new IllegalStateException("Run, auth name, auth uuid, auth token and user type cannot be null values.");
		}
		
		HeadlessMinecraft.launch(run, authName, authUuid, authToken, userType);
	}
	
}
