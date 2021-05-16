package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.net.Proxy;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.OfflineSocialInteractions;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.hycrafthd.event_system.EventHooks;
import net.hycrafthd.event_system.EventManager;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayerSocialManager;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.hycrafthd.headless_minecraft.plugin.PluginManager;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Timer;
import net.minecraft.client.User;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;

public class HeadlessMinecraft extends ReentrantBlockableEventLoop<Runnable> {
	
	private static HeadlessMinecraft INSTANCE;
	
	private EventManager eventManager;
	
	static void launch(File run, String authName, String authUuid, String authToken, String authType) {
		PluginManager.load();
		
		INSTANCE = new HeadlessMinecraft(run, authName, authUuid, authToken, authType);
		
		PluginManager.enable();
		
		while (INSTANCE.isRunning()) {
			INSTANCE.run();
		}
	}
	
	public static HeadlessMinecraft getInstance() {
		return INSTANCE;
	}
	
	private final Thread thread;
	private boolean running;
	private final Timer timer;
	
	private final User user;
	private final MinecraftSessionService sessionService;
	private final HeadlessPlayerSocialManager socialManager;
	
	private final ConnectionManager connectionManager;
	
	public HeadlessMinecraft(File run, String authName, String authUuid, String authToken, String authType) {
		super(Constants.NAME);
		
		eventManager = new EventManager();
		
		thread = Thread.currentThread();
		running = true;
		timer = new Timer(20, 0);
		
		user = new User(authName, authUuid, authToken, authType);
		
		final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
		sessionService = service.createMinecraftSessionService();
		
		SocialInteractionsService socialInteractionService;
		try {
			socialInteractionService = service.createSocialInteractionsService(authToken);
		} catch (AuthenticationException ex) {
			socialInteractionService = new OfflineSocialInteractions();
		}
		
		socialManager = new HeadlessPlayerSocialManager(socialInteractionService);
		
		connectionManager = new ConnectionManager(this);
		
		bootstrapMinecraft();
	}
	
	private void bootstrapMinecraft() {
		Main.LOGGER.info("Started bootstrap for minecraft");
		CrashReport.preload();
		Bootstrap.bootStrap();
		Bootstrap.validate();
		Main.LOGGER.info("Finished bootstrap for minecraft");
	}
	
	private void run() {
		final int ticksToDo = timer.advanceTime(Util.getMillis());
		runAllTasks();
		for (int index = 0; index < Math.min(10, ticksToDo); index++) {
			tick();
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException ex) {
			throw new AssertionError("The main thread should never be interupted!", ex);
		}
	}
	
	private void tick() {
		connectionManager.tick();
		EventHooks.tick();
	}
	
	@Override
	protected Thread getRunningThread() {
		return thread;
	}
	
	@Override
	protected boolean shouldRun(Runnable runnable) {
		return true;
	}
	
	@Override
	protected Runnable wrapRunnable(Runnable runnable) {
		return runnable;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public User getUser() {
		return user;
	}
	
	public MinecraftSessionService getSessionService() {
		return sessionService;
	}
	
	public HeadlessPlayerSocialManager getSocialManager() {
		return socialManager;
	}
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
}
