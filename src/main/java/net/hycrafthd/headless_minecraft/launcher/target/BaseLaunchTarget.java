package net.hycrafthd.headless_minecraft.launcher.target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Main;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.library.DownloadableFile;
import net.hycrafthd.minecraft_downloader.settings.GeneratedSettings;
import net.hycrafthd.minecraft_downloader.settings.LauncherVariables;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public abstract class BaseLaunchTarget implements ILaunchHandlerService {
	
	@Override
	public void configureTransformationClassLoader(ITransformingClassLoaderBuilder builder) {
		final ProvidedSettings settings = MinecraftSetup.getInstance().getSettings();
		final GeneratedSettings generatedSettings = settings.getGeneratedSettings();
		
		// Add all libraries to the transforming path
		generatedSettings.getDownloadableFiles() //
				.stream() //
				.filter(downloadableFile -> !downloadableFile.isNative()) //
				.filter(DownloadableFile::hasDownloadedFile) //
				.map(DownloadableFile::getDownloadedFile) //
				.distinct() //
				.forEach(file -> builder.addTransformationPath(file.toPath()));
		
		configureTargetSpecificTransformationClassLoader(builder);
	}
	
	protected abstract void configureTargetSpecificTransformationClassLoader(ITransformingClassLoaderBuilder builder);
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		Main.LOGGER.info("Authenticate minecraft account");
		
		// Authenticate
		final MinecraftSetup setup = MinecraftSetup.getInstance();
		setup.authenticate();
		final ProvidedSettings settings = setup.getSettings();
		
		final List<String> argsList = new ArrayList<>();
		argsList.add("--auth-name");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_PLAYER_NAME));
		argsList.add("--auth-uuid");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_UUID));
		argsList.add("--auth-token");
		argsList.add(settings.getVariable(LauncherVariables.AUTH_ACCESS_TOKEN));
		argsList.add("--user-type");
		argsList.add(settings.getVariable(LauncherVariables.USER_TYPE));
		
		final String[] args = Stream.concat(argsList.stream(), Stream.of(arguments)).toArray(String[]::new);
		
		return () -> {
			
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.Main", true, launchClassLoader.getInstance());
			entryClass.getMethod("main", String[].class).invoke(null, (Object) args);
			
			return null;
		};
	}
	
}
