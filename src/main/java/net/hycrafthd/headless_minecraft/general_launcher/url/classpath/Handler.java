package net.hycrafthd.headless_minecraft.general_launcher.url.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLStreamHandler;
import java.util.Optional;

import net.hycrafthd.headless_minecraft.launcher.Main;

public class Handler extends URLStreamHandler {
	
	@Override
	protected URLConnection openConnection(URL url) throws IOException {
		return new URLConnection(url) {
			
			@Override
			public void connect() throws IOException {
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				final String resource = URLDecoder.decode(url.getPath(), "UTF-8");
				return Optional.ofNullable(Main.CURRENT_CLASSLOADER.getResourceAsStream(resource)).orElseThrow(() -> new IOException("Resource " + resource + " was not found"));
			}
		};
	}
}
