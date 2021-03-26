package net.hycrafthd.headless_minecraft.launcher;

import java.io.File;

import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftClasspathBuilder;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	static void launch(File run, String username, String password) {
		// Create provided settings
		final ProvidedSettings settings = new ProvidedSettings(Constants.MCVERSION, run, null);
		
		// Download minecraft
		MinecraftParser.launch(settings);
		MinecraftDownloader.launch(settings, true);
		MinecraftClasspathBuilder.launch(settings);
		MinecraftAuthenticator.launch(settings, username, password);
	}
	
}
