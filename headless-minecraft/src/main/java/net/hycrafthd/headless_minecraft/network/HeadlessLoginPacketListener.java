package net.hycrafthd.headless_minecraft.network;

import java.util.function.Consumer;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;

public class HeadlessLoginPacketListener extends ClientHandshakePacketListenerImpl {
	
	private final HeadlessMinecraft headlessMinecraft;
	
	public HeadlessLoginPacketListener(HeadlessMinecraft headlessMinecraft, Connection connection, Consumer<Component> updateStatus) {
		super(connection, null, null, updateStatus);
		this.headlessMinecraft = headlessMinecraft;
	}
	
	@Override
	public void onDisconnect(Component component) {
		// TODO
	}
	
	@Override
	public void handleGameProfile(ClientboundGameProfilePacket packet) {
//		this.updateStatus.accept(new TranslatableComponent("connect.joining"));
//		this.localGameProfile = clientboundGameProfilePacket.getGameProfile();
//		this.connection.setProtocol(ConnectionProtocol.PLAY);
//		this.connection.setListener(new ClientPacketListener(this.minecraft, this.parent, this.connection, this.localGameProfile));
	}
}
