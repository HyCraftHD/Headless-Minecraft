package net.hycrafthd.headless_minecraft.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

@Mixin(ClientLevel.class)
abstract class ClientLevelMixin {
	
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;"))
	private Minecraft returnNullForMinecraftInstance() {
		return null;
	}
	
	// Remove tintCaches
	
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;make(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;"))
	private Object returnNullForUtilMake(Object object, Consumer<?> consumer) {
		return null;
	}
	
	@Overwrite
	public void onChunkLoaded(int x, int z) {
	}
	
	@Overwrite
	public void clearTintCaches() {
	}
}
