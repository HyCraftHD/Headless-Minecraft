package net.hycrafthd.headless_minecraft.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.hycrafthd.headless_minecraft.impl.FakeTutorial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.tutorial.Tutorial;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
	
	@Redirect(method = "*", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/player/LocalPlayer;"))
	private LocalPlayer redirectPlayerFieldAccess(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "*", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;"))
	private ClientLevel redirectLevelFieldAccess(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"))
	private Tutorial redirectGetTutorialMethodeInvocation(Minecraft minecraft) {
		return FakeTutorial.INSTANCE;
	}
	
	@Redirect(method = "continueDestroyBlock", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyTicks:F", ordinal = 0))
	private float removeSoundManagerCall(MultiPlayerGameMode multiPlayerGameMode) {
		return 1; // Return % 4.0F == 0.0F must not succeed
	}
}
