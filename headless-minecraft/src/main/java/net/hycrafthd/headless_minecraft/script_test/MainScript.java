package net.hycrafthd.headless_minecraft.script_test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.hycrafthd.headless_minecraft.script.IScript;

/**
 * This is just a test script to test some stuff.
 * 
 * @author HyCraftHD
 */
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
	public void enable() {
		LOGGER.info("Finished loading main script.");
		final ConnectionManager connectionManager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		connectionManager.updateServerData("localhost");
		connectionManager.connectToServer();
	}
	
}
