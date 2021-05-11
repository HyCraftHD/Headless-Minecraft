package net.hycrafthd.headless_minecraft.launcher.discovery;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class LauncherServiceProvider implements ITransformationService {
	
	private static final Logger LOGGER = LogManager.getLogger("Launcher Service");
	
	@Override
	public String name() {
		return "headless-minecraft";
	}
	
	@Override
	public void onLoad(IEnvironment environment, Set<String> otherServices) throws IncompatibleEnvironmentException {
		LOGGER.info("Initially load headless minecraft launcher service provider");
		if (!otherServices.contains("mixin")) {
			throw new IncompatibleEnvironmentException("Mixin is required to run");
		}
		LOGGER.debug("Other transformation services are " + String.join(", ", otherServices));
	}
	
	@Override
	public void initialize(IEnvironment environment) {
		// final String version = environment.getProperty(IEnvironment.Keys.VERSION.get()).orElseThrow(() -> new
		// IllegalStateException("Version key must be present"));
		// final Path runDirectory = environment.getProperty(IEnvironment.Keys.GAMEDIR.get()).orElseThrow(() -> new
		// IllegalStateException("Run directory key must be present"));
		// LOGGER.debug("Launch headless minecraft version {}", version);
		// LOGGER.debug("The run directory is {}", runDirectory);
	}
	
	@Override
	public List<Entry<String, Path>> runScan(IEnvironment environment) {
		return Arrays.asList(new AbstractMap.SimpleImmutableEntry<>("implementation", Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD)));
	}
	
	@Override
	public void beginScanning(IEnvironment environment) {
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		return Collections.emptyList();
	}
}
