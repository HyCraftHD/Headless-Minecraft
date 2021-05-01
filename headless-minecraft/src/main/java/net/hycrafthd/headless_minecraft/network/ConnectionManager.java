package net.hycrafthd.headless_minecraft.network;

import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessLevel;
import net.hycrafthd.headless_minecraft.impl.HeadlessMultiPlayerGameMode;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;
import net.hycrafthd.headless_minecraft.plugin.newstuff.IConnectionManager;
import net.hycrafthd.headless_minecraft.plugin.newstuff.IHeadlessLevel;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;

public class ConnectionManager implements IConnectionManager {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private final HeadlessMinecraft headlessMinecraft;
	
	private ServerData serverData;
	private Connection connection;
	
	private HeadlessLevel level;
	private HeadlessPlayer player;
	private HeadlessMultiPlayerGameMode gameMode;
	
	public ConnectionManager(HeadlessMinecraft headlessMinecraft) {
		this.headlessMinecraft = headlessMinecraft;
		updateServerData("localhost");
	}
	
	public void tick() {
		if (connection != null && gameMode == null) {
			connection.tick();
		}
		
		if (gameMode != null) {
			gameMode.tick();
		}
		
		if (level != null) {
			if (level.getSkyFlashTime() > 0) {
				level.setSkyFlashTime(level.getSkyFlashTime() - 1);
			}
			
			level.tickEntities();
			try {
				level.tick(() -> true);
			} catch (Throwable throwable) {
				final CrashReport report = CrashReport.forThrowable(throwable, "Exception in world tick");
				if (level == null) {
					final CrashReportCategory category = report.addCategory("Affected level");
					category.setDetail("Problem", (Object) "Level is null!");
				} else {
					level.fillReportDetails(report);
				}
				
				throw new ReportedException(report);
			}
		}
	}
	
	@Override
	public void updateServerData(String ip) {
		serverData = new ServerData("Server with ip: " + ip, ip, false);
	}
	
	@Override
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
	
	public void disconnectFromServer() {
		connection.disconnect(new TextComponent("disconnect"));
	}
	
	public void disconnectedFromServer(Component component) {
		LOGGER.info("Disconnected from server because: {}", component.getString());
		connection = null;
		gameMode = null;
		level = null;
		player = null;
	}
	
	public ServerData getServerData() {
		return serverData;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public HeadlessLevel getLevel() {
		return level;
	}
	
	public HeadlessPlayer getPlayer() {
		return player;
	}
	
	public HeadlessMultiPlayerGameMode getGameMode() {
		return gameMode;
	}
	
	public void setLevel(HeadlessLevel level) {
		this.level = level;
	}
	
	public void setPlayer(HeadlessPlayer player) {
		this.player = player;
	}
	
	public void setGameMode(HeadlessMultiPlayerGameMode gameMode) {
		this.gameMode = gameMode;
	}

	@Override
	public IHeadlessLevel getWorldHandle() {
		return level.getHandle();
	}
}
