package net.hycrafthd.headless_minecraft.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
}
