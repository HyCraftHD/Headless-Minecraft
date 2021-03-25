package net.hycrafthd.headless_minecraft;

import org.spongepowered.asm.mixin.Mixins;

import com.mojang.math.Matrix3f;

import net.hycrafthd.headless_minecraft.mixin.Matrix3fAccessor;

public class Start {
	
	public static void main(String[] args) {
		System.out.println("XXaSKhdjlfsdjf");
		new Start();
	}
	
	public Start() {
		System.out.println(this.getClass().getClassLoader());
		Mixins.addConfiguration("mixin.json");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		
		Matrix3f m = Matrix3f.createScaleMatrix(1, 1, 55);
		
		Object o = m;
		
		System.out.println("_ASDKAJLSDH AKLJSHD AILSDhJAJKLS HDAKLSJDH AKLJHsDJKAL HDAJKLSDHJASDKLJH----------------------------------------------------------------------------------------");
		Mixins.getMixinsForClass("com.mojang.math.Matrix3f").forEach(x -> {
			System.out.println(x);
		});
		
		Matrix3fAccessor x = (Matrix3fAccessor) o;
		
		System.out.println(x);
		System.out.println(x.getM00());
		
		m.adjugateAndDet();
		System.out.println("_________ CHANGED _______________");
		
		System.out.println(x);
		System.out.println(x.getM00());
	}
}
