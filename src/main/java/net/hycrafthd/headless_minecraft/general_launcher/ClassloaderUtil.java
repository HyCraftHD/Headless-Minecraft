package net.hycrafthd.headless_minecraft.general_launcher;

import java.lang.reflect.Method;

public class ClassloaderUtil {
	
	public static ClassLoader getPlatformClassLoaderOrNull() {
		try {
			final Method method = ClassLoader.class.getMethod("getPlatformClassLoader");
			
			try {
				return (ClassLoader) method.invoke(null);
			} catch (Exception ex) {
				throw new IllegalStateException("Method getPlatformClassLoader could not be invoked", ex);
			}
		} catch (NoSuchMethodException ex) {
			// Okay as the method is only useful for jdk9+. For jdk8 we return null
			return null;
		}
	}
	
}
