package net.hycrafthd.headless_minecraft.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.sounds.SoundManager;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
	
	@Shadow(aliases = "ambientSoundHandlers")
	private List<AmbientSoundHandler> ambientSoundHandlers;
	
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getSoundManager()Lnet/minecraft/client/sounds/SoundManager;"))
	private SoundManager replaceSoundManagerCallToNull(Minecraft minecraft) {
		return null;
	}
	
	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void clearAmbientSoundManagers(CallbackInfo callback) {
		ambientSoundHandlers.clear();
	}
	
}
