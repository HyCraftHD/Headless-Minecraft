package net.hycrafthd.headless_minecraft.test_plugin;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.event_system.commands.CommandRegistry;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
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
		HeadlessMinecraft.getInstance().getConnectionManager().updateServerData("mc-project.hycrafthd.net:25566");
		HeadlessMinecraft.getInstance().getConnectionManager().connectToServer();
		HeadlessMinecraft.getInstance().getEventManager().registerListener(new TestListener());
		CommandRegistry.registerCommand("hi", true, (command, args) -> {
			HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat("Command " + command);
			HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat("args " + Arrays.toString(args));
		});
		
		CommandRegistry.registerAlias("hi", "h", "i");
	}
	
}
