package net.hycrafthd.headless_minecraft.script_test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.script.IScript;

public class MainScript implements IScript {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public MainScript() {
		LOGGER.info("Created main script. Classloader: " + this.getClass().getClassLoader());
	}
	
	@Override
	public String name() {
		return "Main Script";
	}
	
	@Override
	public void finishedLoading() {
		LOGGER.info("Finished loading main script.");
	}
	
}
