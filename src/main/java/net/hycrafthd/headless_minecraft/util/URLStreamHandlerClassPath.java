package net.hycrafthd.headless_minecraft.util;

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
				final String resource = URLDecoder.decode(url.getFile(), "UTF-8");
				
				// System.out.println("-------------- Try to get " + resource + " for url " + url);
				
				return Optional.ofNullable(currentClassLoader.getResourceAsStream(resource)).orElseThrow(() -> new IOException("Resource " + resource + " was not found"));
			}
		};
	}
	
	@Override
	protected void parseURL(URL url, String spec, int start, int limit) {
		String file;
		if (spec.startsWith("classpath:"))
			file = spec.substring(10);
		else if ("./".equals(url.getFile()))
			file = spec;
		else if (url.getFile().endsWith("/"))
			file = url.getFile() + spec;
		else if ("#runtime".equals(spec))
			file = url.getFile();
		else
			file = spec;
		setURL(url, "classpath", "", -1, null, null, file, null, null);
	}
}
