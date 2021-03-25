package net.hycrafthd.headless_minecraft.launcher;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.Launcher;

public class Main {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft");
	
	public static void main(String[] args) {
		LOGGER.info("Start headless minecraft launcher");
		try {
			net.hycrafthd.minecraft_downloader.Main.main(new String[] { "" });
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Launcher.main(args);
	}
	
}
