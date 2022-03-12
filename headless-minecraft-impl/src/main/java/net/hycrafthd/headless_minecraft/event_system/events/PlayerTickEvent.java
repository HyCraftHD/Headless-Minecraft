package net.hycrafthd.headless_minecraft.event_system.events;

import net.hycrafthd.headless_minecraft.event_system.Event;
import net.minecraft.world.entity.player.Player;

public abstract class PlayerTickEvent implements Event {

	private Player player;
	
	protected PlayerTickEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static class Pre extends PlayerTickEvent {
		
		public Pre(Player player) {
			super(player);
		}
	}
	
	public static class Post extends PlayerTickEvent {
		
		public Post(Player player) {
			super(player);
		}
	}
}
