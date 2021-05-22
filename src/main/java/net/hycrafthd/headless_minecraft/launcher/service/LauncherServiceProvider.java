package net.hycrafthd.headless_minecraft.launcher.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;

public class LauncherServiceProvider implements ITransformationService {
	
	public static final Logger LOGGER = LogManager.getLogger("Headless Minecraft Launcher Service");
	
	private OptionSpec<File> authFileSpec;
	private OptionSpec<String> authenticateTypeSpec;
	
	private File authFile;
	private String authenticateType;
	
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
		// Validate supplied parameters
		final String version = environment.getProperty(IEnvironment.Keys.VERSION.get()).orElseThrow(() -> new IllegalStateException("Version key must be present"));
		final Path runDirectory = environment.getProperty(IEnvironment.Keys.GAMEDIR.get()).orElseThrow(() -> new IllegalStateException("Run directory key must be present"));
		if (authFile == null) {
			throw new IllegalStateException("Auth file cannot be null");
		}
		
		// Log information
		LOGGER.debug("Launch headless minecraft version {}", version);
		LOGGER.debug("The run directory is {}", runDirectory.toAbsolutePath());
		LOGGER.debug("The auth file is {}", authFile.toPath().toAbsolutePath());
		
		// Minecraft setup
		LOGGER.debug("Setup minecraft environment");
		MinecraftSetup.setup(runDirectory.toFile(), authFile, authenticateType != null, authenticateType);
	}
	
	@Override
	public void beginScanning(IEnvironment environment) {
	}
	
	@Override
	public List<Entry<String, Path>> runScan(IEnvironment environment) {
		beginScanning(environment);
		
		try {
			final URL url = LauncherServiceProvider.class.getResource("/META-INF/MANIFEST.MF");
			System.out.println(url);
			
			URI uri = url.toURI();
			Map<String, String> env = Collections.emptyMap();
			FileSystem zipfs = FileSystems.newFileSystem(uri, env);
			
			System.out.println(zipfs);
			System.out.println(zipfs.provider());
			
			final Path path = zipfs.getPath("headless_minecraft_implementation-1.16.5-1.0.0-SNAPSHOT.jar");
			System.out.println(path);
			
			return Arrays.asList(new AbstractMap.SimpleImmutableEntry<>("implementation", path));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new IllegalStateException("XOX");
		
		// return Arrays.asList(new AbstractMap.SimpleImmutableEntry<>("implementation",
		// Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD)), new AbstractMap.SimpleImmutableEntry<>("plugin",
		// Paths.get(Constants.DEVELOPMENT_TEST_PLUGIN_BUILD)));
	}
	
	@Override
	public void arguments(BiFunction<String, String, OptionSpecBuilder> argumentBuilder) {
		authFileSpec = argumentBuilder.apply("auth-file", "Authentication file for reading and updating authentication data").withRequiredArg().required().ofType(File.class);
		authenticateTypeSpec = argumentBuilder.apply("authenticate", "Shows an interactive console login for mojang and microsoft accounts (Only console is allowed currently)").withRequiredArg();
	}
	
	@Override
	public void argumentValues(OptionResult option) {
		authFile = option.value(authFileSpec);
		authenticateType = option.value(authenticateTypeSpec);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		return Collections.emptyList();
	}
}
