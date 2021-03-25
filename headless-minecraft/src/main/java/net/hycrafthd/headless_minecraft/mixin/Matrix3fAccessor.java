package net.hycrafthd.headless_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.math.Matrix3f;

@Mixin(Matrix3f.class)
public interface Matrix3fAccessor {
	
	@Accessor("m00")
	public float getM00();
	
}
