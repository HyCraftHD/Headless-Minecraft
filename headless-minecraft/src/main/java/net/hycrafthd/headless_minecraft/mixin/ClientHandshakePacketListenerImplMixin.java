package net.hycrafthd.headless_minecraft.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.chat.Component;

@Mixin(ClientHandshakePacketListenerImpl.class)
public abstract class ClientHandshakePacketListenerImplMixin {
	
	@Accessor("updateStatus")
	public abstract Consumer<Component> getUpdateStatus();
	
	@Redirect(method = "authenticateServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getUser()Lnet/minecraft/client/User;"))
	private User getHeadlessUser(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getUser();
	}
	
	@Overwrite(aliases = "getMinecraftSessionService")
	private MinecraftSessionService getMinecraftSessionService() {
		return HeadlessMinecraft.getInstance().getSessionService();
	}
	
}
