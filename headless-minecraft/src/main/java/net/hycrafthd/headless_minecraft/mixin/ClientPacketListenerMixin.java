package net.hycrafthd.headless_minecraft.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.util.thread.BlockableEventLoop;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
	
	@Redirect(method = "handleAddEntity", at = @At(value = "INVOKE", opcode = Opcodes.INVOKESTATIC, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"))
	private <T extends PacketListener> void replaceEnsureRunningOnSameThread(Packet<T> packet, T listener, BlockableEventLoop<?> unused) {
		PacketUtils.ensureRunningOnSameThread(packet, listener, HeadlessMinecraft.getInstance());
	}
	
}
