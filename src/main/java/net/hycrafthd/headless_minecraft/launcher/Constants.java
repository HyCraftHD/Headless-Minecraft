package net.hycrafthd.headless_minecraft.launcher;

public class Constants {
	
	public static final String MCVERSION = "1.16.5";
	
	public static final boolean DEVELOPMENT_MODE = Boolean.getBoolean("headless-minecraft.development");
	
	// Development mode values
	public static final String DEVELOPMENT_DOWNLOAD_DIRECTORY = System.getProperty("headless-minecraft.development.download");
	public static final String DEVELOPMENT_MAPPED_MINECRAFT = System.getProperty("headless-minecraft.development.mapped-minecraft");
}
