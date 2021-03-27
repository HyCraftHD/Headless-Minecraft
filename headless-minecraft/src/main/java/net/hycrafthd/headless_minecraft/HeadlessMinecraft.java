package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft.CrashReport;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.server.Bootstrap;

public class HeadlessMinecraft {
	
	private static HeadlessMinecraft INSTANCE;
	
	static void launch(File run, String authName, String authUuid, String authToken, String authType) {
		INSTANCE = new HeadlessMinecraft(run, new User(authName, authUuid, authToken, authType));
	}
	
	public static HeadlessMinecraft getInstance() {
		return INSTANCE;
	}
	
	private final User user;
	private final MinecraftSessionService sessionService;
	
	public HeadlessMinecraft(File run, User user) {
		this.user = user;
		
		sessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY).createMinecraftSessionService();
		
		bootstrapMinecraft();
		
		// HotbarManager
	}
	
	private void bootstrapMinecraft() {
		CrashReport.preload();
		Bootstrap.bootStrap();
		Bootstrap.validate();
	}
	
	private void connect(String host, int port) {
		InetAddress inetAddress = null;
		Connection connection = null;
		try {
			inetAddress = InetAddress.getByName(host);
			connection = Connection.connectToServer(inetAddress, port, true); // true = Linux packet optimisation
			connection.send(new ClientIntentionPacket(host, port, ConnectionProtocol.LOGIN));
			connection.send(new ServerboundHelloPacket(user.getGameProfile()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
