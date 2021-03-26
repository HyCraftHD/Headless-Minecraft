package net.hycrafthd.headless_minecraft;

import org.spongepowered.asm.mixin.Mixins;

import com.mojang.math.Matrix3f;

import net.hycrafthd.headless_minecraft.mixin.Matrix3fAccessor;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("YES THIS IS MY MAIN");
		System.out.println(Main.class.getClassLoader());
		
		Mixins.addConfiguration("mixin.json");
		
		System.out.println("Added mixin");
		
		final Matrix3f matrix = Matrix3f.createScaleMatrix(1, 1, 55);
		
		final Matrix3fAccessor accessor = (Matrix3fAccessor) (Object) matrix;
		
		System.out.println("TEST MIXIN");
		
		System.out.println(matrix);
		System.out.println(accessor.getM00());
		
		matrix.adjugateAndDet();
		System.out.println("_________ CHANGED _______________");
		
		System.out.println(matrix);
		System.out.println(accessor.getM00());
		
	}
	
}
