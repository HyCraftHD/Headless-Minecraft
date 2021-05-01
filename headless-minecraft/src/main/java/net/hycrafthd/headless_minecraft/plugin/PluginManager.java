package net.hycrafthd.headless_minecraft.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginManager {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private static final ScriptClassLoader CLASSLOADER = new ScriptClassLoader();
	private static final List<File> SCRIPT_CANIDATES = new ArrayList<>();
	private static final List<IScript> LOADED_SCRIPTS = new ArrayList<>();
	
	public static final String PLUGIN_FOLDER = "D:\\Dokumente\\Minecraft\\Minecraft Bot Java\\plugins";
	public static final String MANIFEST_KEY = "Test-Key";
	
	public static void load() {
		LOGGER.info("Started to load scripts");
		
		// File pluginsFolder = new File(PLUGIN_FOLDER);
		//
		// List<File> files = Arrays.asList(pluginsFolder.listFiles());
		//
		// files.stream().filter(e -> FilenameUtils.getExtension(e.getName()).equals("jar")).forEach(e -> {
		// try {
		// CLASSLOADER.addURL(e.toURI().toURL());
		// } catch (MalformedURLException ex) {
		// throw new RuntimeException(ex);
		// }
		// SCRIPT_CANIDATES.add(e);
		// });
		//
		// SCRIPT_CANIDATES.forEach(e -> {
		// try (final JarFile jarFile = new JarFile(e)) {
		// final Class<?> clazz = Class.forName((String) jarFile.getManifest().getMainAttributes().getValue(MANIFEST_KEY), true,
		// CLASSLOADER);
		// // TODO add to loaded scripts
		// } catch (IOException | ClassNotFoundException ex) {
		// throw new RuntimeException(ex);
		// }
		// });
		
		HeadlessPlugin test = null;
		System.out.println(test);
		System.out.println(HeadlessPlugin.class);
		System.out.println(HeadlessPlugin.class.getClassLoader());
		
		try {
			Class<?> clazz = Class.forName("net.hycrafthd.headless_minecraft.test_plugin.TestPlugin");
			System.out.println(clazz);
			System.out.println(clazz.getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// TODO remove test
		try {
			// LOADED_SCRIPTS.add(Class.forName("net.hycrafthd.headless_minecraft.test_plugin.TestPlugin").newInstance());
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		
		LOGGER.info("Finished loading scripts");
	}
	
	public static void enable() {
		LOGGER.info("Finished loading minecraft. Call script finished loading methods");
		LOADED_SCRIPTS.forEach(IScript::enable);
	}
}
