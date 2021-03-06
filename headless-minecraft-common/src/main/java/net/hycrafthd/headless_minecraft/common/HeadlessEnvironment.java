package net.hycrafthd.headless_minecraft.common;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Supplier;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.TypesafeMap;

public class HeadlessEnvironment {
	
	public static final Supplier<TypesafeMap.Key<String>> VERSION = IEnvironment.Keys.VERSION;
	
	public static final Supplier<TypesafeMap.Key<Path>> GAME_DIR = IEnvironment.Keys.GAMEDIR;
	
	public static final Supplier<TypesafeMap.Key<Path>> CACHE_DIR = IEnvironment.buildKey("cachedir", Path.class);
	
	public static final Supplier<TypesafeMap.Key<Path>> MINECRAFT_INSTALLATION_DIR = IEnvironment.buildKey("minecraftinstallationdir", Path.class);
	
	public static final Supplier<TypesafeMap.Key<Path>> AUTH_FILE = IEnvironment.buildKey("authfile", Path.class);
	
	public static final Supplier<TypesafeMap.Key<Path>> PLUGIN_DIR = IEnvironment.buildKey("plugindir", Path.class);
	
	public static final Supplier<TypesafeMap.Key<Set<PluginFile>>> PLUGINS = IEnvironment.buildKey("plugins", Set.class);
	
}
