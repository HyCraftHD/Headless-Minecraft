package net.hycrafthd.headless_minecraft.launcher.loading;

import java.nio.file.Path;

import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;

public class DirectoryPluginFile extends PluginFile {
	
	DirectoryPluginFile(Path path) {
		super(path, ManifestUtil.manifestOfDirectory(path));
	}
	
}
