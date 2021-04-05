package net.hycrafthd.headless_minecraft.network;

import java.util.function.Consumer;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.mixin.ClientHandshakePacketListenerImplMixin;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
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
		final Consumer<Component> updateStatus = ((ClientHandshakePacketListenerImplMixin) (Object) this).getUpdateStatus();
		final Connection connection = getConnection();
		
		updateStatus.accept(new TranslatableComponent("connect.joining"));
		connection.setProtocol(ConnectionProtocol.PLAY);
		connection.setListener(new HeadlessPacketListener(headlessMinecraft, connection, packet.getGameProfile()));
	}
}
