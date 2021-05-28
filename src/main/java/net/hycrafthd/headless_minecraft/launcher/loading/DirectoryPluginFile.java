package net.hycrafthd.headless_minecraft.launcher.loading;

import java.nio.file.Path;

import net.hycrafthd.headless_minecraft.common.PluginFile;
import net.hycrafthd.headless_minecraft.common.util.ManifestUtil;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class DirectoryPluginFile extends PluginFile {
	
	DirectoryPluginFile(Path path) {
		super(path, ManifestUtil.manifestOfDirectory(path), Constants.PLUGIN_MAIN_CLASS);
	}
	
}
