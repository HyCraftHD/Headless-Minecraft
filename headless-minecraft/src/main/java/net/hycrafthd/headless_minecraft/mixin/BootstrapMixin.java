package net.hycrafthd.headless_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.Bootstrap;

@Mixin(Bootstrap.class)
abstract class BootstrapMixin {
	
	@Inject(method = "wrapStreams", cancellable = true, at = @At("HEAD"))
	private static void removeStreamWrapping(CallbackInfo info) {
		info.cancel();
	}
	
}
