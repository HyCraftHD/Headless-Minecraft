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
import net.hycrafthd.headless_minecraft.common.util.URLUtil;
import net.hycrafthd.headless_minecraft.launcher.url.classpath.Handler;
import net.hycrafthd.logging_util.LoggingUtil;
import net.hycrafthd.minecraft_downloader.util.FileUtil;

public class Main {
	
	public static final ClassLoader CURRENT_CLASSLOADER = Main.class.getClassLoader();
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft Launcher");
	
	public static void main(String[] args) throws IOException {
		LoggingUtil.redirectPrintStreams(LOGGER);
		
		final OptionParser parser = new OptionParser();
		parser.allowsUnrecognizedOptions();
		
		// Default specs
		final OptionSpec<Void> helpSpec = parser.accepts("help", "Show the help menu").forHelp();
		final OptionSpec<File> runSpec = parser.accepts("run", "Run directory for headless minecraft").withRequiredArg().required().ofType(File.class);
		
		final OptionSet set = parser.parse(args);
		
		if (set.has(helpSpec) || set.specs().size() < 1) {
			parser.printHelpOn(IoBuilder.forLogger(LOGGER).setAutoFlush(true).setLevel(Level.ERROR).buildPrintStream());
			return;
		}
		
		LOGGER.info("Start headless minecraft launcher");
		
		// Get arguments
		final File run = set.valueOf(runSpec);
		
		// Create output folder
		if (FileUtil.createFolders(run)) {
			LOGGER.debug("Created run folder " + run.getAbsolutePath());
		}
		
		// Setup url classpath url stream handler
		URLUtil.addUrlHandler(Handler.class);
		
		// Build argument list
		final List<String> argList = new ArrayList<>();
		argList.add("--launchTarget");
		argList.add(Constants.DEVELOPMENT_MODE ? "development-client" : "production-client");
		argList.add("--gameDir");
		argList.add(run.getPath());
		argList.add("--version");
		argList.add(Constants.MCVERSION);
		
		// Add arguments from main launch except the run parameter
		int skipIndex = -1;
		for (int index = 0; index < args.length; index++) {
			final String arg = args[index];
			if (arg.startsWith("-run") || arg.startsWith("--run")) {
				skipIndex = index + 1;
				continue;
			}
			if (index == skipIndex) {
				continue;
			}
			argList.add(arg);
		}
		
		Launcher.main(argList.stream().toArray(String[]::new));
	}
	
}
