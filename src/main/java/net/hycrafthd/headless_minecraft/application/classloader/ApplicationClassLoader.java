package net.hycrafthd.headless_minecraft.application.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class ApplicationClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public ApplicationClassLoader() {
		super(new URL[] {}, ApplicationClassLoader.class.getClassLoader());
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
	
}
