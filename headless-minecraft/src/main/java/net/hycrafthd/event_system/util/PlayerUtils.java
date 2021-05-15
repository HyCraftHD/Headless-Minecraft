package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;

public class PlayerUtils {
	
	private HeadlessPlayer player;
	
	public PlayerUtils(HeadlessPlayer player) {
		this.player = player;
	}
	
	public PlayerUtils() {
		this.player = HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
	}
	
	public void setPitchYaw(float pitch, float yaw) {
		player.xRot = pitch;
		player.yRot = yaw;
	}
	
}
