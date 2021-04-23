package net.hycrafthd.headless_minecraft.general_launcher;

import java.net.URLStreamHandler;

public class URLUtil {
	
	private static final String PACKAGE_HANDLER_PROPERTY = "java.protocol.handler.pkgs";
	
	public static void addUrlHandler(Class<? extends URLStreamHandler> handlerClass) {
		final String property = System.getProperty(PACKAGE_HANDLER_PROPERTY, "");
		
		final String handlerPackage = handlerClass.getPackage().getName();
		final int endIndex = handlerPackage.lastIndexOf('.');
		
		System.setProperty(PACKAGE_HANDLER_PROPERTY, handlerPackage.substring(0, endIndex) + (property.isEmpty() ? "" : "|" + property));
	}
	
}
