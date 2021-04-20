package net.hycrafthd.headless_minecraft.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ManifestReader {
	
	public static Manifest readManifest(Class<?> clazz) {
		final URL url = clazz.getResource("/" + JarFile.MANIFEST_NAME);
		
		try (final InputStream inputStream = url.openStream()) {
			return new Manifest(inputStream);
		} catch (final IOException ex) {
			throw new IllegalStateException("Cannot read manifest", ex);
		}
	}
	
}
