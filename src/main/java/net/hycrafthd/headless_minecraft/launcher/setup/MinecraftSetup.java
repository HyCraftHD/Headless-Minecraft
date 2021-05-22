package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.File;

import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.service.LauncherServiceProvider;
import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	private static MinecraftSetup INSTANCE;
	
	public static MinecraftSetup launch(File run, File authFile, boolean authenticate, String authenticateType) {
		return INSTANCE = new MinecraftSetup(run, authFile, authenticate, authenticateType);
	}
	
	public static void destroy() {
		INSTANCE = null;
	}
	
	public static MinecraftSetup getInstance() {
		return INSTANCE;
	}
	
	private final ProvidedSettings settings;
	
	public MinecraftSetup(File run, File authFile, boolean authenticate, String authenticateType) {
		LauncherServiceProvider.LOGGER.info("Verify minecraft installation and authenticate");
		
		final File outputDirectory;
		if (Constants.DEVELOPMENT_MODE) {
			outputDirectory = new File(Constants.DEVELOPMENT_DOWNLOAD_DIRECTORY);
		} else {
			outputDirectory = new File(run, "minecraft_files");
		}
		
		// Create provided settings
		settings = new ProvidedSettings(Constants.MCVERSION, outputDirectory, null);
		
		// Download minecraft
		MinecraftParser.launch(settings);
		MinecraftDownloader.launch(settings, true);
		
		// Authenticate
		MinecraftAuthenticator.launch(settings, authFile, authenticate, authenticateType);
		
		LauncherServiceProvider.LOGGER.info("Finished minecraft installation and authenticated");
	}
	
	public ProvidedSettings getSettings() {
		return settings;
	}
	
}
