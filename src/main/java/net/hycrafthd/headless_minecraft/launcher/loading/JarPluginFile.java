package net.hycrafthd.headless_minecraft.launcher.loading;

import java.nio.file.Path;
import java.util.jar.JarFile;

import net.hycrafthd.headless_minecraft.common.PluginFile;
import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class JarPluginFile extends PluginFile {
	
	private final JarFile jarFile;
	
	JarPluginFile(Path path, JarFile jarFile) {
		super(path, ManifestUtil.manifestOfJarFile(jarFile), Constants.PLUGIN_MAIN_CLASS);
		this.jarFile = jarFile;
	}
	
	public JarFile getJarFile() {
		return jarFile;
	}
}
