package net.hycrafthd.headless_minecraft.main_plugin;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.hycrafthd.event_system.EventHandler;
import net.hycrafthd.event_system.commands.CommandRegistry;
import net.hycrafthd.event_system.commands.CommandRegistry.CommandRegisterException;
import net.hycrafthd.event_system.events.PlayerTickEvent;
import net.hycrafthd.event_system.util.PlayerUtils;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.plugin.HeadlessPlugin;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.InteractionHand;

public class MainPlugin implements HeadlessPlugin {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private boolean holdRight = false;
	
	private boolean move = false;
	
	private float impuls = 0.01F;
	
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
			CommandRegistry.registerCommand("place", true, (command, args, player) -> {
				if (args.length == 0)
					System.out.println(PlayerUtils.rightClick(InteractionHand.MAIN_HAND));
			});
			
			CommandRegistry.registerCommand("break", true, (command, args, player) -> {
				if (args.length == 0)
					PlayerUtils.breakBlockFacing(() -> {
					});
			});
			
			CommandRegistry.registerCommand("slot", true, (command, args, player) -> {
				if (args.length == 1) {
					PlayerUtils.selectHotbarSlot(Integer.parseInt(args[0]));
				}
			});
			
			CommandRegistry.registerCommand("attack", true, (command, args, player) -> {
				if (args.length == 0) {
					PlayerUtils.attackEntity();
				}
			});
			
			CommandRegistry.registerCommand("facing", true, (command, args, player) -> {
				if (args.length == 2)
					PlayerUtils.setPitchYaw(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
			});
			
			CommandRegistry.registerCommand("continue", true, (command, args, player) -> {
				if (args.length == 0) {
					holdRight = !holdRight;
					PlayerUtils.chat("" + holdRight);
				}
			});
			
			CommandRegistry.registerCommand("drop", true, (command, args, player) -> {
				if (args.length == 1) {
					PlayerUtils.dropSelected(Boolean.parseBoolean(args[0]));
				}
			});
			
			CommandRegistry.registerCommand("swap", true, (command, args, player) -> {
				if (args.length == 0) {
					PlayerUtils.swapHand();
				}
			});
			
			CommandRegistry.registerCommand("teleport", true, (command, args, player) -> {
				if (args.length == 2) {
					PlayerUtils.teleportTo(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
				}
			});
			
			CommandRegistry.registerCommand("align", true, (command, args, player) -> {
				if (args.length == 2) {
					PlayerUtils.alignOnBlock(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
				}
			});
			
			CommandRegistry.registerCommand("looking", true, (command, args, player) -> {
				if (args.length == 0) {
					PlayerUtils.getBlockLookingAt().ifPresent(e -> PlayerUtils.chat(e.toString()));
				}
			});
			
			CommandRegistry.registerCommand("testChat", true, (command, args, player) -> {
				if (args.length == 0) {
					for (int i = 0; i < 100; i++) {
						PlayerUtils.chat(randomString());
					}
				}
			});
			
		} catch (CommandRegisterException e) {
		}
	}
	
	@EventHandler
	public void tick(PlayerTickEvent.Post event) {
		if (holdRight) {
			PlayerUtils.rightClick(InteractionHand.MAIN_HAND);
			System.out.println();
		}
		
		if (move) {
			
		}
	}
	
	private String randomString() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		
		String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		
		return generatedString;
	}
}
