package net.hycrafthd.headless_minecraft.network;

import java.math.BigInteger;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import com.mojang.authlib.exceptions.AuthenticationException;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.HttpUtil;

public class ClientHandshakeListener implements ClientLoginPacketListener {
	
	private Connection connection;
	private HeadlessMinecraft headlessMinecraft;
	
	public ClientHandshakeListener(Connection connection, HeadlessMinecraft headlessMinecraft) {
		this.connection = connection;
		this.headlessMinecraft = headlessMinecraft;
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
	public void handleHello(ClientboundHelloPacket packet) {
		System.out.println("handleHello");
		
		final SecretKey secretKey;
		final PublicKey publicKey;
		
		final String serverId;
		final Cipher cipherDecrypt;
		final Cipher cipherEncrypt;
		
		final ServerboundKeyPacket serverboundKeyPacket;
		try {
			secretKey = Crypt.generateSecretKey();
			publicKey = packet.getPublicKey();
			serverId = new BigInteger(Crypt.digestData(packet.getServerId(), publicKey, secretKey)).toString(16);
			cipherDecrypt = Crypt.getCipher(Cipher.DECRYPT_MODE, secretKey);
			cipherEncrypt = Crypt.getCipher(Cipher.ENCRYPT_MODE, secretKey);
			serverboundKeyPacket = new ServerboundKeyPacket(secretKey, publicKey, packet.getNonce());
		} catch (CryptException e) {
			throw new IllegalStateException("Protocol error", e);
		}
		
		HttpUtil.DOWNLOAD_EXECUTOR.submit(() -> {
			try {
				headlessMinecraft.getSessionService().joinServer(headlessMinecraft.getUser().getGameProfile(), headlessMinecraft.getUser().getAccessToken(), serverId);
			} catch (AuthenticationException e) {
				connection.disconnect(new TextComponent(e.getMessage()));
				throw new IllegalStateException(e);
			}
			
			this.connection.send(serverboundKeyPacket, (future) -> {
				this.connection.setEncryptionKey(cipherDecrypt, cipherEncrypt);
			});
		});
	}
	
}
