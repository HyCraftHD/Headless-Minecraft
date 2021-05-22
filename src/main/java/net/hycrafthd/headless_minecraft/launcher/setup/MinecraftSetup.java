package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.File;

import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.Main;
import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	private static MinecraftSetup INSTANCE;
	
	public static MinecraftSetup launch(File run, File authFile, boolean authenticate, String authenticateType) {
		return INSTANCE = new MinecraftSetup(run, authFile, authenticate, authenticateType);
	}
	
	public static MinecraftSetup getInstance() {
		return INSTANCE;
	}
	
	private final ProvidedSettings settings;
	
	private final File authFile;
	private final boolean authenticate;
	private final String authenticateType;
	
	public MinecraftSetup(File run, File authFile, boolean authenticate, String authenticateType) {
		Main.LOGGER.info("Verify minecraft installation");
		
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
		
		this.authFile = authFile;
		this.authenticate = authenticate;
		this.authenticateType = authenticateType;
	}
	
	public void authenticate() {
		MinecraftAuthenticator.launch(settings, authFile, authenticate, authenticateType);
	}
	
	public ProvidedSettings getSettings() {
		return settings;
	}
	
}
