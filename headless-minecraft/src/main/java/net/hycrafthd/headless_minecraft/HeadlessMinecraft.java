package net.hycrafthd.headless_minecraft;

import java.io.File;

import net.minecraft.client.User;

public class HeadlessMinecraft {
	
	private static HeadlessMinecraft INSTANCE;
	
	static void launch(File run, String authName, String authUuid, String authToken, String authType) {
		INSTANCE = new HeadlessMinecraft(run, new User(authName, authUuid, authToken, authType));
	}
	
	public static HeadlessMinecraft getInstance() {
		return INSTANCE;
	}
	
	private final User user;
	
	public HeadlessMinecraft(File run, User user) {
		this.user = user;
	}
	
}
