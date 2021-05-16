package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;

public class PlayerUtils {
	
	public static void breakBlock() {
		HeadlessPlayer player = getPlayer();
		HeadlessMinecraft.getInstance().getConnectionManager().getGameMode();
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
