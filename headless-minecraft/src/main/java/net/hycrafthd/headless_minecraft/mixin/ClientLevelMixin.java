package net.hycrafthd.headless_minecraft.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
	
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;"))
	private Minecraft returnNullForMinecraftInstance() {
		return null;
	}
	
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;make(Ljava/lang/Object;Ljava/util/function/Consumer;)Ljava/lang/Object;"))
	private Object returnNullForUtilMake(Object object, Consumer<?> consumer) {
		return null;
	}
	
	@Inject(method = "fillReportDetails", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;fillReportDetails(Lnet/minecraft/CrashReport;)Lnet/minecraft/CrashReportCategory;"))
	public void replaceCrashReportDetails(CrashReport report, CallbackInfoReturnable<CrashReportCategory> callback, CrashReportCategory category) {
		category.setDetail("Server brand", () -> {
			return "UNKNOWN YET"; // TODO
		});
		category.setDetail("Server type", () -> {
			return "Multiplayer";
		});
		callback.setReturnValue(category);
		callback.cancel();
	}
	
}
