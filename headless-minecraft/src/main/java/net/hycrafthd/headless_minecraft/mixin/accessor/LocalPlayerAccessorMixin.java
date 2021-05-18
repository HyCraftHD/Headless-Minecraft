package net.hycrafthd.headless_minecraft.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessorMixin {
	
	@Accessor("autoJumpEnabled")
	boolean getAutoJumpEnabled();
	
	@Accessor("autoJumpEnabled")
	void setAutoJumpEnabled(boolean value);
	
}
