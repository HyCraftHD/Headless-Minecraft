package net.hycrafthd.headless_minecraft.launcher;

import java.util.jar.Attributes;

public class Constants {
	
	public static final String MCVERSION = "1.16.5";
	
	public static final boolean DEVELOPMENT_MODE = Boolean.getBoolean("headless-minecraft.development");
	
	// Development mode values
	public static final String DEVELOPMENT_DOWNLOAD_DIRECTORY = System.getProperty("headless-minecraft.development.download");
	public static final String DEVELOPMENT_MAPPED_MINECRAFT = System.getProperty("headless-minecraft.development.mapped-minecraft");
	public static final String DEVELOPMENT_API_BUILD = System.getProperty("headless-minecraft.development.api-build");
	public static final String DEVELOPMENT_IMPLEMENTATION_BUILD = System.getProperty("headless-minecraft.development.implementation-build");
	public static final String DEVELOPMENT_TEST_PLUGIN_BUILD = System.getProperty("headless-minecraft.development.test-plugin-build");
	
	// Production manifest value
	public static final Attributes.Name PRODUCTION_IMPLEMENTATION_JAR = new Attributes.Name("Headless-Minecraft-Implementation-Jar");
}