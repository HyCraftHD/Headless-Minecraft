package net.hycrafthd.headless_minecraft.test_plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;

import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.commands.CommandRegistry;
import net.hycrafthd.event_system.commands.CommandRegistry.CommandRegisterException;
import net.hycrafthd.event_system.events.PlayerTickEvent;
import net.hycrafthd.event_system.util.BlockBreakUtil;
import net.hycrafthd.event_system.util.BlockPlaceUtil;
import net.hycrafthd.event_system.util.PlayerUtils;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.plugin.HeadlessPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class TestPlugin implements HeadlessPlugin {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private boolean breakBlock = false;
	
	private BlockHitResult testbhr;
	
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
		HeadlessMinecraft.getInstance().getEventManager().registerListener(this);
		try {
			CommandRegistry.registerCommand("place", true, (command, args) -> {
				if (args.length == 0)
					System.out.println(BlockPlaceUtil.placeBlock(InteractionHand.MAIN_HAND));
			});
			
			CommandRegistry.registerCommand("slot", true, (command, args) -> {
				if (args.length == 1) {
					PlayerUtils.selectHotbarSlot(Integer.parseInt(args[0]));
				}
			});
			
			CommandRegistry.registerCommand("attack", true, (command, args) -> {
				if (args.length == 0) {
					PlayerUtils.attackEntity();
				}
			});
			
		} catch (CommandRegisterException e) {
		}
	}
	
	@EventHandler
	public void testContinueBreak(PlayerTickEvent.Post event) {
		
	}
}
