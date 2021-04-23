package net.hycrafthd.headless_minecraft.application.classloader;

import java.net.URL;
import java.net.URLClassLoader;

import net.hycrafthd.headless_minecraft.general_launcher.ClassloaderUtil;

public class ApplicationClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	private final ClassLoader currentClassLoader = getClass().getClassLoader();
	
	public ApplicationClassLoader() {
		super(new URL[0], ClassloaderUtil.getPlatformClassLoaderOrNull());
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if (name.equalsIgnoreCase("net.hycrafthd.headless_minecraft.application.classloader.ApplicationClassLoader")) {
			return this.getClass();
		} else if (name.startsWith("net.hycrafthd.headless_minecraft.application")) {
			throw new ClassNotFoundException("net.hycrafthd.headless_minecraft.application package should not be accessed with the application classloader");
		} else if (name.startsWith("net.hycrafthd.headless_minecraft.general_launcher")) {
			return currentClassLoader.loadClass(name);
		} else {
			return super.loadClass(name, resolve);
		}
	}
}
