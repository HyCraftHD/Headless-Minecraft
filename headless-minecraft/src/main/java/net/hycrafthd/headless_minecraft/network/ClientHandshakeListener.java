package net.hycrafthd.headless_minecraft.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;

public class ClientHandshakeListener implements ClientLoginPacketListener {
	
	private Connection connection;
	
	public ClientHandshakeListener(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void onDisconnect(Component var1) {
		System.out.println("onDisconnect");
	}
	
	@Override
	public void handleCompression(ClientboundLoginCompressionPacket var1) {
		System.out.println("handleCompression");
	}
	
	@Override
	public void handleCustomQuery(ClientboundCustomQueryPacket var1) {
		System.out.println("handleCustomQuery");
	}
	
	@Override
	public void handleDisconnect(ClientboundLoginDisconnectPacket var1) {
		System.out.println("handleDisconnect");
	}
	
	@Override
	public void handleGameProfile(ClientboundGameProfilePacket var1) {
		System.out.println("handleGameProfile");
	}
	
	@Override
	public void handleHello(ClientboundHelloPacket var1) {
		System.out.println("handleHello");
	}
	
}
