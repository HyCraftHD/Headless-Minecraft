package net.hycrafthd.headless_minecraft.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ManifestReader {
	
	public static Manifest readManifest(Class<?> clazz) {
		final URL url = clazz.getResource("/" + JarFile.MANIFEST_NAME);
		
		try (final InputStream inputStream = url.openStream()) {
			return new Manifest(inputStream);
		} catch (final IOException ex) {
			throw new IllegalStateException("Cannot read manifest", ex);
		}
	}
	
	public static Collection<Manifest> findManifests() throws IOException {
		final List<Manifest> manifests = new ArrayList<>();
		
		toStream(Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME)).forEach(url -> {
			try (final InputStream inputStream = url.openStream()) {
				if (inputStream == null) {
					return;
				}
				manifests.add(new Manifest(inputStream));
			} catch (IOException ex) {
				throw new IllegalStateException("An error occured while searching for manifest file", ex);
			}
		});
		
		return manifests;
	}
	
	private static <T> Stream<T> toStream(Enumeration<T> enumeration) {
		return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED) {
			
			@Override
			public boolean tryAdvance(Consumer<? super T> consumer) {
				final boolean moreElements = enumeration.hasMoreElements();
				if (moreElements) {
					consumer.accept(enumeration.nextElement());
				}
				return moreElements;
			}
			
			@Override
			public void forEachRemaining(Consumer<? super T> consumer) {
				while (enumeration.hasMoreElements()) {
					consumer.accept(enumeration.nextElement());
				}
			}
		}, false);
	}
	
}
