package net.hycrafthd.headless_minecraft.launcher.target;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import cpw.mods.modlauncher.api.ILaunchHandlerService;
import cpw.mods.modlauncher.api.ITransformingClassLoader;
import cpw.mods.modlauncher.api.ITransformingClassLoaderBuilder;
import net.hycrafthd.headless_minecraft.launcher.Constants;
import net.hycrafthd.headless_minecraft.launcher.Main;
import net.hycrafthd.headless_minecraft.launcher.setup.MinecraftSetup;
import net.hycrafthd.minecraft_downloader.library.DownloadableFile;
import net.hycrafthd.minecraft_downloader.settings.GeneratedSettings;
import net.hycrafthd.minecraft_downloader.settings.ProvidedSettings;

public class DevelopmentLaunchTarget implements ILaunchHandlerService {
	
	@Override
	public String name() {
		return "development";
	}
	
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
				.forEach(file -> builder.addTransformationPath(file.toPath()));
		
		// Add mapped minecraft jar
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_MAPPED_MINECRAFT));
		
		// Add headless minecraft implementation
		builder.addTransformationPath(Paths.get(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD));
		
		// Set manifest locators
		Manifest loadManifest = null;
		
		try (InputStream inputStream = new FileInputStream(new File(Constants.DEVELOPMENT_IMPLEMENTATION_BUILD, JarFile.MANIFEST_NAME))) {
			loadManifest = new Manifest(inputStream);
		} catch (IOException ex) {
			Main.LOGGER.error("Could not get develop manifest", ex);
		}
		
		final Manifest manifest = loadManifest;
		
		System.out.println(manifest.getMainAttributes().keySet().iterator().next());
		System.out.println(manifest.getMainAttributes().getValue("MixinConnector"));
		
		builder.setManifestLocator(connection -> {
			if (connection instanceof JarURLConnection) {
				try {
					return Optional.ofNullable(((JarURLConnection) connection).getManifest());
				} catch (IOException ex) {
					return Optional.empty();
				}
			}
			
			System.out.println("RETURNED MANIFEST: ->>>>>>>>>>>>>> " + manifest + " -> " + connection);
			return Optional.ofNullable(manifest);
		});
	}
	
	@Override
	public Callable<Void> launchService(String[] arguments, ITransformingClassLoader launchClassLoader) {
		MinecraftSetup.destroy();
		
		// launchClassLoader.addTargetPackageFilter(filter -> !filter.startsWith("net.hycrafthd.headless_minecraft.mixin"));
		
		return () -> {
			
			final Class<?> entryClass = Class.forName("net.hycrafthd.headless_minecraft.Main", true, launchClassLoader.getInstance());
			entryClass.getMethod("main", String[].class).invoke(null, (Object) arguments);
			
			return null;
		};
	}
	
}
