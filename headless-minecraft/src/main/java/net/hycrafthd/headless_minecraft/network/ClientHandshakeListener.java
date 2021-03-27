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
	
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDisconnect(Component var1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleCompression(ClientboundLoginCompressionPacket var1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleCustomQuery(ClientboundCustomQueryPacket var1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleDisconnect(ClientboundLoginDisconnectPacket var1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleGameProfile(ClientboundGameProfilePacket var1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleHello(ClientboundHelloPacket var1) {
		// TODO Auto-generated method stub
		
	}
	
}
