package net.hycrafthd.headless_minecraft.launcher.discovery;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import net.hycrafthd.headless_minecraft.launcher.Constants;

public class PluginDiscovery implements ITransformationService {
	
	@Override
	public String name() {
		return "headless-minecraft";
	}
	
	@Override
	public void onLoad(IEnvironment environment, Set<String> otherServices) throws IncompatibleEnvironmentException {
	}
	
	@Override
	public void initialize(IEnvironment environment) {
	}
	
	@Override
	public void beginScanning(IEnvironment environment) {
	}
	
	@Override
	public List<Entry<String, Path>> runScan(IEnvironment environment) {
		return Arrays.asList(new AbstractMap.SimpleImmutableEntry<>("implementation", Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD)));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		return Collections.emptyList();
	}
	
}
