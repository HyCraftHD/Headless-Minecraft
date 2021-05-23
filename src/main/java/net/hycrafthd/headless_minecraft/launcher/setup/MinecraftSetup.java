package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.File;

import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	private static MinecraftSetup INSTANCE;
	
	public static MinecraftSetup initialize(File outputDir, File authFile, boolean authenticate, String authenticateType) {
		return INSTANCE = new MinecraftSetup(outputDir, authFile, authenticate, authenticateType);
	}
	
	public static MinecraftSetup getInstance() {
		return INSTANCE;
	}
	
	private final ProvidedSettings settings;
	
	private final File authFile;
	private final boolean authenticate;
	private final String authenticateType;
	
	public MinecraftSetup(File outputDir, File authFile, boolean authenticate, String authenticateType) {
		this.authFile = outputDir;
		this.authenticate = authenticate;
		this.authenticateType = authenticateType;
		settings = new ProvidedSettings(Constants.MCVERSION, outputDir, null);
	}
	
	public void download() {
		MinecraftParser.launch(settings);
		MinecraftDownloader.launch(settings, true);
	}
	
	public void authenticate() {
		MinecraftAuthenticator.launch(settings, authFile, authenticate, authenticateType);
	}
	
	public ProvidedSettings getSettings() {
		return settings;
	}
	
}
