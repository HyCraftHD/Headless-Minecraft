package net.hycrafthd.headless_minecraft.mixin;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

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
	
	@ModifyConstant(method = "startRiding", constant = @Constant(classValue = AbstractMinecart.class, ordinal = 0))
	public boolean removeRidingSound(Object entity, Class<?> constant) {
		return false;
	}
	
	@Inject(method = "sendPosition", cancellable = true, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "minecraft"))
	private void autoJumpDisabled(CallbackInfo callback) {
		callback.cancel();
	}
	
}
