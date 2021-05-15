package net.hycrafthd.event_system;

import net.hycrafthd.event_system.util.PlayerUtils;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;

public interface Event {
	
	default HeadlessPlayer getPlayer() {
		return HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
	}
	
	/**
	 * TODO singleton!!!
	 */
	default PlayerUtils getPlayerUtils() {
		return new PlayerUtils(getPlayer());
	}
	
}
