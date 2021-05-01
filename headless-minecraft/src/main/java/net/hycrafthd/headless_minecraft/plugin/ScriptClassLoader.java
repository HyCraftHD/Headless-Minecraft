package net.hycrafthd.headless_minecraft.plugin;

import java.net.URL;
import java.net.URLClassLoader;

class ScriptClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public ScriptClassLoader() {
		super(new URL[0], ScriptClassLoader.class.getClassLoader());
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
}