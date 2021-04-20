package net.hycrafthd.headless_minecraft.application;

import java.net.URL;

import net.hycrafthd.headless_minecraft.application.util.URLStreamHandlerClassPath;

public class Main {
	
	public static void main(String[] args) {
		URL.setURLStreamHandlerFactory(protocol -> "classpath".equals(protocol) ? new URLStreamHandlerClassPath() : null);
	}
	
}
