package net.hycrafthd.headless_minecraft.application.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLStreamHandler;
import java.util.Optional;

public class URLStreamHandlerClassPath extends URLStreamHandler {
	
	private final ClassLoader currentClassLoader = getClass().getClassLoader();
	
	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		return new URLConnection(url) {
			
			@Override
			public void connect() throws IOException {
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				final String resource = URLDecoder.decode(url.getHost() + url.getPath(), "UTF-8");
				return Optional.ofNullable(currentClassLoader.getResourceAsStream(resource)).orElseThrow(() -> new IOException("Resource " + resource + " was not found"));
			}
		};
	}
}
