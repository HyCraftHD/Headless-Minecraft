package net.hycrafthd.headless_minecraft.script;

import java.net.URL;
import java.net.URLClassLoader;

class ScriptClassLoader extends URLClassLoader {
	
	static {
		ClassLoader.registerAsParallelCapable();
	}
	
	public ScriptClassLoader() {
		super(new URL[] {});
	}
}