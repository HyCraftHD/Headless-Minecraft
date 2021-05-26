package net.hycrafthd.headless_minecraft.launcher.loading;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

public class LauncherServiceProvider implements ITransformationService {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private OptionSpec<File> minecraftInstallationDirSpec;
	private OptionSpec<File> authFileSpec;
	private OptionSpec<String> authenticateTypeSpec;
	private OptionSpec<File> pluginDirSpec;
	private OptionSpec<File> extraPluginFilesSpec;
	
	private File minecraftInstallationDir;
	private File authFile;
	private String authenticateType;
	private File pluginDir;
	private List<File> extraPluginFiles;
	
	@Override
	public String name() {
		return "mc";
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
		LOGGER.info("Initialize headless minecraft launcher service provider");
		HeadlessMinecraftLoader.initialize(environment, minecraftInstallationDir, authFile, authenticateType, pluginDir, extraPluginFiles);
	}
	
	@Override
	public void beginScanning(IEnvironment environment) {
		HeadlessMinecraftLoader.beginScanning(environment);
	}
	
	@Override
	public List<Entry<String, Path>> runScan(IEnvironment environment) {
		LOGGER.info("Scan for required jars and discover plugins");
		beginScanning(environment);
		return HeadlessMinecraftLoader.runScanning(environment);
	}
	
	@Override
	public void arguments(BiFunction<String, String, OptionSpecBuilder> argumentBuilder) {
		minecraftInstallationDirSpec = argumentBuilder.apply("minecraft-installation", "Directory where minecraft will be installed. Can be shared between instances").withRequiredArg().ofType(File.class);
		authFileSpec = argumentBuilder.apply("auth-file", "Authentication file for reading and updating authentication data").withRequiredArg().required().ofType(File.class);
		authenticateTypeSpec = argumentBuilder.apply("authenticate", "Shows an interactive console login for mojang and microsoft accounts (Only console is allowed currently)").withRequiredArg();
		pluginDirSpec = argumentBuilder.apply("plugin", "Directory where where plugins are searched").withRequiredArg().ofType(File.class);
		extraPluginFilesSpec = argumentBuilder.apply("extra-plugin-file", "Extra jar file or directory (does not search for jar files, uses it as classpath) where to look for plugins").withRequiredArg().ofType(File.class);
	}
	
	@Override
	public void argumentValues(OptionResult option) {
		minecraftInstallationDir = option.value(minecraftInstallationDirSpec);
		authFile = option.value(authFileSpec);
		authenticateType = option.value(authenticateTypeSpec);
		pluginDir = option.value(pluginDirSpec);
		extraPluginFiles = option.values(extraPluginFilesSpec);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		return Collections.emptyList();
	}
}
