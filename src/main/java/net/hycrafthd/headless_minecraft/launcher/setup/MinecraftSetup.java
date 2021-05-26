package net.hycrafthd.headless_minecraft.launcher.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.minecraft_downloader.MinecraftAuthenticator;
import net.hycrafthd.minecraft_downloader.MinecraftDownloader;
import net.hycrafthd.minecraft_downloader.MinecraftParser;
import net.hycrafthd.minecraft_downloader.settings.LauncherVariables;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class MinecraftSetup {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private final ProvidedSettings settings;
	
	private final File authFile;
	private final boolean authenticate;
	private final String authenticateType;
	
	public MinecraftSetup(File outputDir, File authFile, boolean authenticate, String authenticateType) {
		this.authFile = authFile;
		this.authenticate = authenticate;
		this.authenticateType = authenticateType;
		settings = new ProvidedSettings(Constants.MCVERSION, outputDir, null);
	}
	
	public void download() {
		LOGGER.info("Verify or download required minecraft files");
		MinecraftParser.launch(settings);
		MinecraftDownloader.launch(settings, true);
	}
	
	public void authenticate() {
		MinecraftAuthenticator.launch(settings, authFile, authenticate, authenticateType);
	}
	
	public List<String> buildAuthenticationArgs() {
		final List<String> argsList = new ArrayList<>();
		argsList.add("--auth-name");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_PLAYER_NAME));
		argsList.add("--auth-uuid");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_UUID));
		argsList.add("--auth-token");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_ACCESS_TOKEN));
		argsList.add("--user-type");
		argsList.add(settings.getVariable(LauncherVariables.USER_TYPE));
		return argsList;
	}
	
	public ProvidedSettings getSettings() {
		return settings;
	}
	
}
