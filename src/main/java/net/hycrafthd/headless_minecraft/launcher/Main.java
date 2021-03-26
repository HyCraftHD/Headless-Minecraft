package net.hycrafthd.headless_minecraft.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.hycrafthd.logging_util.LoggingUtil;
import net.hycrafthd.minecraft_downloader.util.FileUtil;

public class Main {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft");
	
	public static void main(String[] args) throws IOException {
		LoggingUtil.redirectPrintStreams(LOGGER);
		
		final OptionParser parser = new OptionParser();
		
		// Default specs
		final OptionSpec<Void> helpSpec = parser.accepts("help", "Show the help menu").forHelp();
		final OptionSpec<File> runSpec = parser.accepts("run", "Run directory for headless minecraft").withRequiredArg().ofType(File.class);
		
		// Login specs
		final OptionSpec<String> usernameSpec = parser.accepts("username", "Username / Email for login").withRequiredArg();
		final OptionSpec<String> passwordSpec = parser.accepts("password", "Password for login").withRequiredArg();
		
		final OptionSet set = parser.parse(args);
		
		if (set.has(helpSpec) || set.specs().size() < 3) {
			parser.printHelpOn(IoBuilder.forLogger(LOGGER).setAutoFlush(true).setLevel(Level.ERROR).buildPrintStream());
			return;
		}
		
		LOGGER.info("Start headless minecraft launcher");
		
		// Get arguments
		final File run = set.valueOf(runSpec);
		
		final String username = set.valueOf(usernameSpec);
		final String password = set.valueOf(passwordSpec);
		
		// Validate that run, username and password are not null
		if (run == null || username == null || password == null) {
			throw new IllegalStateException("Run, username and password cannot be null values.");
		}
		
		// Create output folder
		if (FileUtil.createFolders(run)) {
			LOGGER.debug("Created run folder " + run.getAbsolutePath());
		}
		
		// Minecraft setup
		MinecraftSetup.launch(run, username, password);
		
		// Launcher.main(args);
	}
	
}
