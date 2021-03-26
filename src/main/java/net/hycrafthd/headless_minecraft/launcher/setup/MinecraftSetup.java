package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.File;

import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftClasspathBuilder;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	private static MinecraftSetup INSTANCE;
	
	public static MinecraftSetup launch(File run, String username, String password) {
		return INSTANCE = new MinecraftSetup(run, username, password);
	}
	
	public static void destroy() {
		INSTANCE = null;
	}
	
	public static MinecraftSetup getInstance() {
		return INSTANCE;
	}
	
	private final ProvidedSettings settings;
	
	public MinecraftSetup(File run, String username, String password) {
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
		MinecraftClasspathBuilder.launch(settings);
		MinecraftAuthenticator.launch(settings, username, password);
	}
	
	public ProvidedSettings getSettings() {
		return settings;
	}
	
}