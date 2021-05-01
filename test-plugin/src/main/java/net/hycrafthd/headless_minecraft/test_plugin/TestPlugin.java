package net.hycrafthd.headless_minecraft.test_plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.plugin.HeadlessEventBus;
import net.hycrafthd.headless_minecraft.plugin.HeadlessPlugin;
import net.hycrafthd.headless_minecraft.plugin.newstuff.IHeadlessMinecraft;

public class TestPlugin implements HeadlessPlugin {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void load() {
		LOGGER.info("Load test plugin");
	}
	
	@Override
	public void enable(IHeadlessMinecraft headlessMinecraft) {
		LOGGER.info("Enable test plugin");
		headlessMinecraft.getConnectionManager().updateServerData("localhost");
		headlessMinecraft.getConnectionManager().connectToServer();
	}
	
	@Override
	public void register(HeadlessEventBus eventBus) {
	}
	
}
