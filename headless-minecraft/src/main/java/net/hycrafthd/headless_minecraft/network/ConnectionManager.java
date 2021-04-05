package net.hycrafthd.headless_minecraft.network;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;

public class ConnectionManager {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private final HeadlessMinecraft headlessMinecraft;
	
	private ServerData serverData;
	private Connection connection;
	
	public ConnectionManager(HeadlessMinecraft headlessMinecraft) {
		this.headlessMinecraft = headlessMinecraft;
	}
	
	public void tick() {
		if (connection != null) {
			connection.tick();
		}
	}
	
	public void connectToServer() {
		final ServerAddress serverAddress = ServerAddress.parseString(serverData.ip);
		
		LOGGER.info("Try to connect to " + serverAddress.getHost() + ":" + serverAddress.getPort());
		
		try {
			connection = Connection.connectToServer(InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort(), true);
			connection.setListener(new HeadlessLoginPacketListener(headlessMinecraft, connection, component -> LOGGER.info("Connection state: {}", component.getString())));
			
			LOGGER.info("Connection initiated, start handshake");
			
			connection.send(new ClientIntentionPacket(serverAddress.getHost(), serverAddress.getPort(), ConnectionProtocol.LOGIN));
			connection.send(new ServerboundHelloPacket(headlessMinecraft.getUser().getGameProfile()));
		} catch (final Exception ex) {
			LOGGER.warn("Could not connect to server", ex);
		}
	}
	
	public void disconnectFromServer(Component component) {
		LOGGER.info("Disconnected from server because: {}", component.getString());
		connection = null;
	}
	
	public ServerData getServerData() {
		return serverData;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
