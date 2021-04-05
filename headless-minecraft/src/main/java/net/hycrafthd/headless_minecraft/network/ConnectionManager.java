package net.hycrafthd.headless_minecraft.network;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Connection;

public class ConnectionManager {
	
	private ServerData serverData;
	private Connection connection;
	
	public ServerData getServerData() {
		return serverData;
	}
	
	public void setServerData(ServerData serverData) {
		this.serverData = serverData;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void tick() {
		
	}
	
}
