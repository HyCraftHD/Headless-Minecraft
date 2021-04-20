package net.hycrafthd.headless_minecraft.application.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class ApplicationClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public ApplicationClassLoader() {
		super(new URL[0]);
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
}
