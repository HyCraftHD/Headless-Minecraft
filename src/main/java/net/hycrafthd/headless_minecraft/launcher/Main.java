package net.hycrafthd.headless_minecraft.launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import cpw.mods.modlauncher.Launcher;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.headless_minecraft.launcher.url.classpath.Handler;
import net.hycrafthd.headless_minecraft.launcher.util.URLUtil;
import net.hycrafthd.logging_util.LoggingUtil;
import net.hycrafthd.minecraft_downloader.settings.LauncherVariables;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;
import net.hycrafthd.minecraft_downloader.util.FileUtil;

public class Main {
	
	public static final ClassLoader CURRENT_CLASSLOADER = Main.class.getClassLoader();
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft Launcher");
	
	public static void main(String[] args) throws IOException {
		LoggingUtil.redirectPrintStreams(LOGGER);
		
		final OptionParser parser = new OptionParser();
		
		// Default specs
		final OptionSpec<Void> helpSpec = parser.accepts("help", "Show the help menu").forHelp();
		final OptionSpec<File> runSpec = parser.accepts("run", "Run directory for headless minecraft").withRequiredArg().ofType(File.class);
		
		// Login specs
		final OptionSpec<File> authFileSpec = parser.accepts("auth-file", "Authentication file for reading and updating authentication data").withRequiredArg().required().ofType(File.class);
		final OptionSpec<String> authenticateSpec = parser.accepts("authenticate", "Shows an interactive console login for mojang and microsoft accounts (soon a gui solution may be implemented)").availableIf(authFileSpec).withOptionalArg();
		
		final OptionSet set = parser.parse(args);
		
		if (set.has(helpSpec) || set.specs().size() < 3) {
			parser.printHelpOn(IoBuilder.forLogger(LOGGER).setAutoFlush(true).setLevel(Level.ERROR).buildPrintStream());
			return;
		}
		
		LOGGER.info("Start headless minecraft launcher");
		
		// Get arguments
		final File run = set.valueOf(runSpec);
		
		final File authFile = set.valueOf(authFileSpec);
		final boolean authenticate = set.has(authenticateSpec);
		final String authenticateType = set.valueOf(authenticateSpec);
		
		// Validate that run, and auth file != null
		if (run == null || authFile == null) {
			throw new IllegalStateException("Run and auth file cannot be null values.");
		}
		
		// Create output folder
		if (FileUtil.createFolders(run)) {
			LOGGER.debug("Created run folder " + run.getAbsolutePath());
		}
		
		// Minecraft setup
		final MinecraftSetup setup = MinecraftSetup.launch(run, authFile, authenticate, authenticateType);
		
		// Setup url classpath url stream handler
		URLUtil.addUrlHandler(Handler.class);
		
		// Setup args
		final ProvidedSettings settings = setup.getSettings();
		
		final List<String> argList = new ArrayList<>();
		argList.add("--launchTarget");
		argList.add(Constants.DEVELOPMENT_MODE ? "development-client" : "production-client");
		argList.add("--gameDir");
		argList.add(run.getPath());
		argList.add("--version");
		argList.add(Constants.MCVERSION);
		argList.add("--auth-name");
		argList.add(settings.getVariable(LauncherVariables.AUTH_PLAYER_NAME));
		argList.add("--auth-uuid");
		argList.add(settings.getVariable(LauncherVariables.AUTH_UUID));
		argList.add("--auth-token");
		argList.add(settings.getVariable(LauncherVariables.AUTH_ACCESS_TOKEN));
		argList.add("--user-type");
		argList.add(settings.getVariable(LauncherVariables.USER_TYPE));
		
		Launcher.main(argList.stream().toArray(String[]::new));
	}
	
}
