package net.hycrafthd.headless_minecraft.launcher.loading;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.TypesafeMap;
import net.hycrafthd.headless_minecraft.common.HeadlessEnvironment;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.setup.HeadlessMinecraftSetup;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.util.FileUtil;

public class HeadlessMinecraftLoader {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static MinecraftSetup minecraftSetup;
	private static HeadlessMinecraftSetup headlessMinecraftSetup;
	private static PluginLoader pluginLoader;
	
	static void initialize(IEnvironment environment, File minecraftInstallationDir, File authFile, String authenticateType, File pluginDir, List<File> extraPluginFiles) {
		// Validate and compute supplied parameters
		final String version = environment.getProperty(HeadlessEnvironment.VERSION.get()).orElseThrow(() -> new IllegalStateException("Version key must be present"));
		final Path gameDirectory = environment.getProperty(HeadlessEnvironment.GAME_DIR.get()).orElseThrow(() -> new IllegalStateException("Game directory key must be present"));
		
		// Setup cache dir
		final File cacheDir = gameDirectory.resolve("cache").toFile();
		FileUtil.createFolders(cacheDir);
		final Path cacheDirectory = setProperty(environment, HeadlessEnvironment.CACHE_DIR, cacheDir.toPath());
		
		// Setup minecraft install directory
		if (minecraftInstallationDir == null) {
			if (Constants.DEVELOPMENT_MODE) {
				minecraftInstallationDir = new File(Constants.DEVELOPMENT_DOWNLOAD_DIRECTORY);
			} else {
				minecraftInstallationDir = new File(cacheDir, "minecraft");
			}
		}
		FileUtil.createFolders(minecraftInstallationDir);
		final Path minecraftInstallationDirectory = setProperty(environment, HeadlessEnvironment.MINECRAFT_INSTALLATION_DIR, minecraftInstallationDir.toPath());
		
		// Setup authentication file
		final Path authenticationFile = setProperty(environment, HeadlessEnvironment.AUTH_FILE, authFile.toPath());
		
		// Setup plugin directory
		if (pluginDir == null) {
			pluginDir = new File(gameDirectory.toFile(), "plugins");
		}
		FileUtil.createFolders(pluginDir);
		final Path pluginDirectory = setProperty(environment, HeadlessEnvironment.PLUGIN_DIR, pluginDir.toPath());
		
		// Setup extra plugin files
		final Set<Path> extraPluginPaths = extraPluginFiles.stream().map(File::toPath).collect(Collectors.toSet());
		
		// Initialize minecraft setup
		LOGGER.info("Initialize minecraft setup");
		minecraftSetup = new MinecraftSetup(minecraftInstallationDirectory.toFile(), authenticationFile.toFile(), authenticateType != null, authenticateType);
		
		// Initialize headless minecraft setup
		LOGGER.info("Initialize headless minecraft setup");
		headlessMinecraftSetup = new HeadlessMinecraftSetup(cacheDirectory);
		
		// Initialize plugin loader
		LOGGER.info("Initialize plugin loader");
		pluginLoader = new PluginLoader(pluginDirectory, extraPluginPaths);
		
		// Log information
		LOGGER.info(" --------------------------------------------- ");
		LOGGER.info("Minecraft version is {}", version);
		LOGGER.info("The game directory is {}", gameDirectory.toAbsolutePath());
		LOGGER.info("The cache directory is {}", cacheDirectory.toAbsolutePath());
		LOGGER.info("The minecraft installation directory is {}", minecraftInstallationDirectory.toAbsolutePath());
		LOGGER.info("The auth file is {}", authenticationFile.toAbsolutePath());
		if (authenticateType != null) {
			LOGGER.info("The authentication type is {}", authenticateType);
		}
		LOGGER.info("The plugin directory is {}", pluginDirectory.toAbsolutePath());
		if (!extraPluginPaths.isEmpty()) {
			LOGGER.info("There are {} extra plugin paths", extraPluginPaths.size());
			LOGGER.debug("The extra plugin files are {}", extraPluginPaths);
		}
		LOGGER.info(" --------------------------------------------- ");
		
		minecraftSetup.download();
	}
	
	static void beginScanning(IEnvironment environment) {
		headlessMinecraftSetup.findJars();
		pluginLoader.discover(headlessMinecraftSetup.getFiles());
		pluginLoader.initPluginFiles(environment);
	}
	
	static List<Entry<String, Path>> runScanning(IEnvironment environment) {
		return pluginLoader.getPluginScanEntries();
	}
	
	private static <T> T setProperty(IEnvironment environment, Supplier<TypesafeMap.Key<T>> key, T value) {
		return environment.computePropertyIfAbsent(key.get(), unused -> value);
	}
	
	public static MinecraftSetup getMinecraftSetup() {
		return minecraftSetup;
	}
	
	public static HeadlessMinecraftSetup getHeadlessMinecraftSetup() {
		return headlessMinecraftSetup;
	}
	
	public static PluginLoader getPluginLoader() {
		return pluginLoader;
	}
	
}
