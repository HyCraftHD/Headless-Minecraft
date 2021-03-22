package net.hycrafthd.headless_minecraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.logging.LoggingUtil;

public class Main {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft");
	
	public static void main(String[] args) {
		LoggingUtil.redirectPrintStreams(LOGGER);
	}
	
}
