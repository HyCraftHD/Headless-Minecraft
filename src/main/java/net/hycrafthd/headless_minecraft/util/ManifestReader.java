package net.hycrafthd.headless_minecraft.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

public class ManifestReader {
	
	public static Manifest readManifest(Class<?> clazz) {
		final URL url = clazz.getResource("/" + JarFile.MANIFEST_NAME);
		
		try (final InputStream inputStream = url.openStream()) {
			return new Manifest(inputStream);
		} catch (final IOException ex) {
			throw new IllegalStateException("Cannot read manifest", ex);
		}
	}
	
	public static Collection<Manifest> findManifests() {
		final List<Manifest> manifests = new ArrayList<>();
		
		try (final Stream<URL> stream = StreamUtil.toStream(Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME))) {
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
