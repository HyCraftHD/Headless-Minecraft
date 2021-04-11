package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.net.Proxy;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.hycrafthd.headless_minecraft.script.ScriptManager;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Timer;
import net.minecraft.client.User;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;

public class HeadlessMinecraft extends ReentrantBlockableEventLoop<Runnable> {
	
	private static HeadlessMinecraft INSTANCE;
	
	static void launch(File run, String authName, String authUuid, String authToken, String authType) {
		
		new ClientPacketListener(null, null, null, null);
		
		ScriptManager.load();
		
		INSTANCE = new HeadlessMinecraft(run, authName, authUuid, authToken, authType);
		
		ScriptManager.enable();
		
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
	
	private final ConnectionManager connectionManager;
	
	public HeadlessMinecraft(File run, String authName, String authUuid, String authToken, String authType) {
		super(Constants.NAME);
		
		thread = Thread.currentThread();
		running = true;
		timer = new Timer(20, 0);
		
		user = new User(authName, authUuid, authToken, authType);
		sessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY).createMinecraftSessionService();
		
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
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
}
