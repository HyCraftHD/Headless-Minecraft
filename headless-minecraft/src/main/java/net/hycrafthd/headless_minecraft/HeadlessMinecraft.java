package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.hycrafthd.headless_minecraft.network.ClientHandshakeListener;
import net.minecraft.CrashReport;
import net.minecraft.client.User;
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
		
		// connect("mc-project.hycrafthd.net", 25566);
		// HotbarManager
	}
	
	private void bootstrapMinecraft() {
		Main.LOGGER.info("Start Bootstrap");
		CrashReport.preload();
		Bootstrap.bootStrap();
		Bootstrap.validate();
		Main.LOGGER.info("Finish Bootstrap");
	}
	
	private void connect(String host, int port) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(host);
			final Connection connection = Connection.connectToServer(inetAddress, port, false); // true = Linux packet optimisation
			Main.LOGGER.info("Connected");
			connection.setListener(new ClientHandshakeListener(connection, this));
			connection.send(new ClientIntentionPacket(host, port, ConnectionProtocol.LOGIN));
			connection.send(new ServerboundHelloPacket(user.getGameProfile()));
			
			Executors.newScheduledThreadPool(2).scheduleAtFixedRate(() -> {
				connection.tick();
				// System.out.println("TICK: rec: " + connection.getAverageReceivedPackets() + " -> send: " +
				// connection.getAverageSentPackets());
				
			}, 50, 50, TimeUnit.MILLISECONDS);
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public MinecraftSessionService getSessionService() {
		return sessionService;
	}
}
