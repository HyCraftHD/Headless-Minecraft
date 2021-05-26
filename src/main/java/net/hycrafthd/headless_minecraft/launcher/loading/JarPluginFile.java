package net.hycrafthd.headless_minecraft.launcher.loading;

import java.nio.file.Path;
import java.util.jar.JarFile;

import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;

public class JarPluginFile extends PluginFile {
	
	private final JarFile jarFile;
	
	JarPluginFile(Path path, JarFile jarFile) {
		super(path, ManifestUtil.manifestOfJarFile(jarFile));
		this.jarFile = jarFile;
	}
	
	public JarFile getJarFile() {
		return jarFile;
	}
}
