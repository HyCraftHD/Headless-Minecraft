package net.hycrafthd.headless_minecraft.launcher;

import java.util.jar.Attributes;

public class Constants {
	
	public static final String MCVERSION = "1.16.5";
	
	public static final boolean DEVELOPMENT_MODE = Boolean.getBoolean("headless-minecraft.development");
	
	// Development mode values
	public static final String DEVELOPMENT_DOWNLOAD_DIRECTORY = System.getProperty("headless-minecraft.development.download");
	public static final String DEVELOPMENT_MAPPED_MINECRAFT = System.getProperty("headless-minecraft.development.mapped-minecraft");
	public static final String DEVELOPMENT_IMPLEMENTATION_BUILD = System.getProperty("headless-minecraft.development.implementation-build");
	public static final String DEVELOPMENT_MAIN_PLUGIN_BUILD = System.getProperty("headless-minecraft.development.main-plugin-build");
	
	// Production manifest value
	public static final Attributes.Name PRODUCTION_IMPLEMENTATION_JAR = new Attributes.Name("Headless-Minecraft-Implementation-Jar");
	public static final Attributes.Name PRODUCTION_MAIN_PLUGIN_JAR = new Attributes.Name("Headless-Minecraft-Main-Plugin-Jar");
	
	// Plugin manifest main class entry
	public static final Attributes.Name PLUGIN_MAIN_CLASS = new Attributes.Name("Headless-Minecraft-Plugin-Main-Class");
}