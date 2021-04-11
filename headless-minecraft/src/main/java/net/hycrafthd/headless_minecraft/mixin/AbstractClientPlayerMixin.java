package net.hycrafthd.headless_minecraft.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.resources.ResourceLocation;

@Mixin(AbstractClientPlayer.class)
abstract class AbstractClientPlayerMixin {
	
	@Redirect(method = "*", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;getConnection()Lnet/minecraft/client/multiplayer/ClientPacketListener;"))
	private ClientPacketListener replaceGetConnection(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().connection;
	}
	
	@Overwrite
	public static HttpTexture registerSkinTexture(ResourceLocation resourceLocation, String uuid) {
		return null;
	}
	
	@Overwrite
	public float getFieldOfViewModifier() {
		return 0;
	}
	
}
