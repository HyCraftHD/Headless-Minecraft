package net.hycrafthd.event_system.events;

import net.hycrafthd.event_system.Event;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;

public abstract class PlayerTickEvent implements Event {
	
	private HeadlessPlayer player;
	
	protected PlayerTickEvent(HeadlessPlayer player) {
		this.player = player;
	}
	
	public HeadlessPlayer getPlayer() {
		return player;
	}
	
	public static class Pre extends PlayerTickEvent {
		
		public Pre(HeadlessPlayer player) {
			super(player);
		}
	}
	
	public static class Post extends PlayerTickEvent {
		
		public Post(HeadlessPlayer player) {
			super(player);
		}
	}
}
