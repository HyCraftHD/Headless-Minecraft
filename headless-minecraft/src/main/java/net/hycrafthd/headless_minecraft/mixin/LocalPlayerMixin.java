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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
	
	@Shadow(aliases = "ambientSoundHandlers")
	private List<AmbientSoundHandler> ambientSoundHandlers;
	
	@Shadow(aliases = "portalTime")
	private float portalTime;
	
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
	private void autoJumpDisable(CallbackInfo callback) {
		callback.cancel();
	}
	
	@Inject(method = "onSyncedDataUpdated", cancellable = true, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "minecraft"))
	private void elytraSoundDisable(CallbackInfo callback) {
		callback.cancel();
	}
	
	@Redirect(method = "handleNetherPortalClient", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "screen", ordinal = 0))
	private Screen removeCloseScreenAndContainer(Minecraft minecraft) {
		return null;
	}
	
	@ModifyConstant(method = "handleNetherPortalClient", constant = @Constant(floatValue = 0.0F, ordinal = 0))
	private float removePortalTriggerSound(float constant) {
		return portalTime + 1;
	}
	
	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;", ordinal = 0))
	private Tutorial removeMinecraftGetTutorialCall(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/tutorial/Tutorial;onInput(Lnet/minecraft/client/player/Input;)V", ordinal = 0))
	private void removeTutorialOnInputCall(Tutorial minecraft, Input input) {
	}
	
}
