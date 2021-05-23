package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft");
	
	public static void main(String[] args) throws IOException {
		final OptionParser parser = new OptionParser();
		
		// Default specs
		final OptionSpec<File> runSpec = parser.accepts("gameDir", "Run directory for headless minecraft").withRequiredArg().ofType(File.class);
		
		// User data specs
		final OptionSpec<String> authNameSpec = parser.accepts("auth-name", "Player name").withRequiredArg();
		final OptionSpec<String> authUuidSpec = parser.accepts("auth-uuid", "Player uuid").withRequiredArg();
		final OptionSpec<String> authTokenSpec = parser.accepts("auth-token", "Auth / Access token").withRequiredArg();
		final OptionSpec<String> userTypeSpec = parser.accepts("user-type", "User type").withRequiredArg();
		
		parser.allowsUnrecognizedOptions();
		
		final OptionSet set = parser.parse(args);
		
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
		
		final ThreadGroup group = new ThreadGroup(Constants.NAME);
		final Thread clientThread = new Thread(group, () -> HeadlessMinecraft.launch(run, authName, authUuid, authToken, userType), Constants.NAME);
		clientThread.start();
		
		joinAgain: try {
			clientThread.join();
		} catch (final InterruptedException ex) {
			break joinAgain;
		}
	}
}
