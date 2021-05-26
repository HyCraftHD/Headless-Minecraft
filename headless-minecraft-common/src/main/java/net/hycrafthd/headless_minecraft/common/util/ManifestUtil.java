package net.hycrafthd.headless_minecraft.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

public class ManifestUtil {
	
	public static Collection<Manifest> findClassPathManifests() {
		final List<Manifest> manifests = new ArrayList<>();
		
		try (final Stream<URL> stream = EnumerationUtil.toStream(Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME))) {
			stream.forEach(url -> {
				try (final InputStream inputStream = url.openStream()) {
					if (inputStream == null) {
						return;
					}
					manifests.add(new Manifest(inputStream));
				} catch (IOException ex) {
					throw new IllegalStateException("Could not open manifest input stream", ex);
				}
			});
		} catch (IOException ex) {
			throw new IllegalStateException("An error occured while searching for manifest files", ex);
		}
		
		return manifests;
	}
	
	public static Optional<Manifest> manifestOfJarFile(JarFile file) {
		try {
			return Optional.of(file.getManifest());
		} catch (IOException ex) {
			return Optional.empty();
		}
	}
	
	public static Optional<Manifest> manifestOfDirectory(Path path) {
		try {
			return Optional.of(new Manifest(Files.newInputStream(path.resolve(JarFile.MANIFEST_NAME))));
		} catch (IOException ex) {
			return Optional.empty();
		}
	}
}
