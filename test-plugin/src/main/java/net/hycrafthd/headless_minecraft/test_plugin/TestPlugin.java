package net.hycrafthd.headless_minecraft.test_plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.plugin.HeadlessEventBus;
import net.hycrafthd.headless_minecraft.plugin.HeadlessPlugin;

public class TestPlugin implements HeadlessPlugin {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void load() {
		LOGGER.info("Load test plugin");
	}
	
	@Override
	public void enable() {
		LOGGER.info("Enable test plugin");
	}
	
	@Override
	public void register(HeadlessEventBus eventBus) {
	}
	
}
