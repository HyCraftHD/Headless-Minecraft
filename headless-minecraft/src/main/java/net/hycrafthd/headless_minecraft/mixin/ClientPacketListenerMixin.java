package net.hycrafthd.headless_minecraft.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessRemotePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
	
	@Redirect(method = "*", at = @At(value = "INVOKE", opcode = Opcodes.INVOKESTATIC, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"))
	private <T extends PacketListener> void replaceEnsureRunningOnSameThread(Packet<T> packet, T listener, BlockableEventLoop<?> unused) {
		PacketUtils.ensureRunningOnSameThread(packet, listener, HeadlessMinecraft.getInstance());
	}
	
	@ModifyConstant(method = "handleAddEntity", constant = @Constant(classValue = AbstractMinecart.class, ordinal = 0))
	public boolean removeSoundManagerCallForMinecartEntity(Object entity, Class<?> constant) {
		return false;
	}
	
	@Redirect(method = "handleAddPlayer", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;"))
	private ClientLevel replaceGetLevel(Minecraft minecraft) {
		return null; // Do not care as we will no use the return value (see replaceConstructRemovePlayer)
	}
	
	@Redirect(method = "handleAddPlayer", at = @At(value = "NEW", target = "Lnet/minecraft/client/player/RemotePlayer;"))
	private RemotePlayer replaceConstructRemovePlayer(ClientLevel unused, GameProfile profile) {
		return new HeadlessRemotePlayer(HeadlessMinecraft.getInstance().getConnectionManager().getLevel(), profile);
	}
	
	@Redirect(method = "handleSetCarriedItem", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/player/LocalPlayer;"))
	private LocalPlayer replaceGetPlayer(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
	}
	
}
