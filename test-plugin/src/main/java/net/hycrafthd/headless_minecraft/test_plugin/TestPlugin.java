package net.hycrafthd.headless_minecraft.test_plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.event_system.commands.CommandRegistry;
import net.hycrafthd.event_system.commands.CommandRegistry.CommandRegisterException;
import net.hycrafthd.event_system.util.PlayerUtils;
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
		try {
			CommandRegistry.registerCommand("hi", true, (command, args) -> {
				HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().chat("Recieved");
				if (args.length == 2) {
					PlayerUtils.setPitchYaw(Integer.parseInt(args[0]), Integer.parseInt(args[1])); // TODO float
				}
			});
			
			CommandRegistry.registerAlias("hi", "h", "i");
		} catch (CommandRegisterException e) {
		}
	}
}
