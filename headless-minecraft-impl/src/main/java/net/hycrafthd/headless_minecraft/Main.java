package net.hycrafthd.headless_minecraft;

import net.hycrafthd.headless_minecraft.event_system.EventHandler;
import net.hycrafthd.headless_minecraft.event_system.EventManager;
import net.hycrafthd.headless_minecraft.event_system.commands.CommandExecution;
import net.hycrafthd.headless_minecraft.event_system.events.PlayerTickEvent;
import net.hycrafthd.headless_minecraft.event_system.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Main {

	public static void main(String[] args) {
		net.minecraft.client.main.Main.LOGGER.info("HELLO from main");
		EventManager.registerListener(new CommandExecution());
		new Main();
		net.minecraft.client.main.Main.main(args);
	}
	
	public Main() {
		EventManager.registerListener(this);
	}
	
	@EventHandler
	public void tick(TickEvent event) {
		//System.out.println("Tick");
	}
	
	@EventHandler
	public void playerTick(PlayerTickEvent.Pre event) {
		//System.out.println("PlayerTickPre");
		if(Minecraft.getInstance().hitResult instanceof BlockHitResult result) {
			System.out.println(event.getPlayer().getLevel().getBlockState(result.getBlockPos()));
			
		} else if(Minecraft.getInstance().hitResult instanceof EntityHitResult result) {
			System.out.println(result.getEntity());
		}
	}
	
	@EventHandler
	public void playerTick(PlayerTickEvent.Post event) {
		//System.out.println("PlayerTickPost");
	}
	
}
