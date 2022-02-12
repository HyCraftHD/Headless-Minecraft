package net.hycrafthd.headless_minecraft.event_system.util;

public class SlowChatManager {
	
	private static int count;
	
	public static void tick() {
		if (!PlayerUtils.getMessagesToSend().isEmpty()) {
			if (count++ % 10 == 0) {
				PlayerUtils.chat(PlayerUtils.getMessagesToSend().poll());
			}
		}
	}
}
