package net.hycrafthd.headless_minecraft.application.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ApplicationClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public ApplicationClassLoader() {
		super(new URL[0], getPlatformClassLoader());
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if (name.startsWith("net.hycrafthd.headless_minecraft.application")) {
			throw new ClassNotFoundException("net.hycrafthd.headless_minecraft.application package should not be accessed with the application classloader");
		}
		return super.loadClass(name, resolve);
	}
	
	private static ClassLoader getPlatformClassLoader() {
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
