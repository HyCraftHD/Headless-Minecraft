package net.hycrafthd.headless_minecraft;

import java.lang.reflect.Field;

import net.hycrafthd.headless_minecraft.event_system.EventHandler;
import net.hycrafthd.headless_minecraft.event_system.EventManager;
import net.hycrafthd.headless_minecraft.event_system.commands.CommandExecution;
import net.hycrafthd.headless_minecraft.event_system.commands.CommandRegistry;
import net.hycrafthd.headless_minecraft.event_system.events.PlayerTickEvent;
import net.hycrafthd.headless_minecraft.event_system.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class Main {
	
	public static boolean left_click = false;
	
	public static void main(String[] args) {
		net.minecraft.client.main.Main.LOGGER.info("HELLO from main");
		EventManager.registerListener(new CommandExecution());
		new Main();
		net.minecraft.client.main.Main.main(args);
	}
	
	public Main() {
		EventManager.registerListener(this);
		
		CommandRegistry.registerCommand("break", (command, args, sender) -> {
			Field field;
			try {
				field = Minecraft.class.getDeclaredField("missTime");
				field.setAccessible(true);
				System.out.println("Misstime: " + field.get(Minecraft.getInstance()));
			} catch (Exception e) {
			}
			Minecraft.getInstance().startAttack();
			left_click = true;
			System.out.println("Break");
		});
	}
	
	@EventHandler
	public void tick(TickEvent event) {
		// System.out.println("Tick");
	}
	
	@EventHandler
	public void playerTick(PlayerTickEvent.Pre event) {
		// System.out.println("PlayerTickPre");
		if (Minecraft.getInstance().hitResult instanceof BlockHitResult result) {
			// System.out.println(event.getPlayer().getLevel().getBlockState(result.getBlockPos()));
			
		} else if (Minecraft.getInstance().hitResult instanceof EntityHitResult result) {
			// System.out.println(result.getEntity());
		}
	}
	
	@EventHandler
	public void playerTick(PlayerTickEvent.Post event) {
		// System.out.println("PlayerTickPost");
	}
	
}
