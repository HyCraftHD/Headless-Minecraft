package net.hycrafthd.headless_minecraft.script;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScriptManager {
	
	private static Logger LOGGER = LogManager.getLogger();
	
	private static final ScriptClassLoader CLASSLOADER = new ScriptClassLoader();
	private static final List<String> SCRIPT_CANIDATES = new ArrayList<>();
	private static final List<IScript> LOADED_SCRIPTS = new ArrayList<>();
	
	public static void load() {
		// TODO load stuff
		// For now we just create it here.
		SCRIPT_CANIDATES.add("net.hycrafthd.headless_minecraft.script_test.MainScript");
		
		for (String canidate : SCRIPT_CANIDATES) {
			try {
				final Class<? extends IScript> clazz = Class.forName(canidate, true, CLASSLOADER).asSubclass(IScript.class);
				LOADED_SCRIPTS.add(clazz.getConstructor().newInstance());
			} catch (Exception ex) {
				LOGGER.error("Script canidate {} cannot be loaded", canidate, ex);
			}
		}
	}
	
	public static void finishedLoading() {
		LOADED_SCRIPTS.forEach(IScript::finishedLoading);
	}
}
