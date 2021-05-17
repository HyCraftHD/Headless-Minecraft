package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PlayerUtils {
	
	public static void breakBlockFacing() {
	}
	
	public void placeBlock() {
	}
	
	public static void attackEntity() {
		
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		PickUtil.pick();
		
		HitResult result = PickUtil.getHitResult();
		
		if (result instanceof EntityHitResult) {
			EntityHitResult entityHitResult = (EntityHitResult) result;
			HeadlessMinecraft.getInstance().getConnectionManager().getGameMode().attack(getPlayer(), entityHitResult.getEntity());
		}
		
	}
	
	public static void selectHotbarSlot(int slot) {
		if (slot > -1 && slot < 9)
			getPlayer().inventory.selected = slot;
	}
	
	public static void setPitchYaw(float pitch, float yaw) {
		HeadlessPlayer player = getPlayer();
		player.xRot = pitch;
		player.yRot = yaw;
	}
	
	public static HeadlessPlayer getPlayer() {
		HeadlessPlayer player = HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
		if (player == null) {
			throw new IllegalStateException("Player not connected!");
		}
		return player;
	}
}
