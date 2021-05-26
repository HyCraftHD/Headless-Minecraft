package net.hycrafthd.headless_minecraft.launcher.loading;

import java.nio.file.Path;
import java.util.Optional;
import java.util.jar.Manifest;

public class PluginFile {
	
	private final Path path;
	private final Optional<Manifest> manifest;
	
	private final String fileName;
	
	PluginFile(Path path, Optional<Manifest> manifest) {
		this.path = path;
		this.manifest = manifest;
		
		fileName = path.getFileName().toString();
	}
	
	public Path getPath() {
		return path;
	}
	
	public Optional<Manifest> getManifest() {
		return manifest;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PluginFile other = (PluginFile) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
}
