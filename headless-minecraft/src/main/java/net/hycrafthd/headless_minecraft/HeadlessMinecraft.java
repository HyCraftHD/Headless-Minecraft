package net.hycrafthd.headless_minecraft;

import java.io.File;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.hycrafthd.headless_minecraft.network.HeadlessLoginPacketListener;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Timer;
import net.minecraft.client.User;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;

public class HeadlessMinecraft extends ReentrantBlockableEventLoop<Runnable> {
	
	private static HeadlessMinecraft INSTANCE;
	
	static void launch(File run, String authName, String authUuid, String authToken, String authType) {
		INSTANCE = new HeadlessMinecraft(run, authName, authUuid, authToken, authType);
		
		while (INSTANCE.isRunning()) {
			INSTANCE.run();
		}
	}
	
	public static HeadlessMinecraft getInstance() {
		return INSTANCE;
	}
	
	private final Thread thread;
	
	private boolean running;
	private Timer timer;
	
	private final User user;
	private final MinecraftSessionService sessionService;
	
	public HeadlessMinecraft(File run, String authName, String authUuid, String authToken, String authType) {
		super(Constants.NAME);
		
		thread = Thread.currentThread();
		thread.setName(Constants.NAME);
		
		running = true;
		timer = new Timer(20, 0);
		
		user = new User(authName, authUuid, authToken, authType);
		sessionService = new YggdrasilAuthenticationService(Proxy.NO_PROXY).createMinecraftSessionService();
		
		bootstrapMinecraft();
		
		System.out.println(new TranslatableComponent("connect.negotiating"));
		System.out.println(new TranslatableComponent("connect.negotiating").getString());
		
		// connect("mc-project.hycrafthd.net", 25566);
		// HotbarManager
	}
	
	private void bootstrapMinecraft() {
		Main.LOGGER.info("Start Bootstrap");
		CrashReport.preload();
		Bootstrap.bootStrap();
		Bootstrap.validate();
		Main.LOGGER.info("Finish Bootstrap");
	}
	
	private void connect(String host, int port) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(host);
			final Connection connection = Connection.connectToServer(inetAddress, port, false); // true = Linux packet optimisation
			Main.LOGGER.info("Connected");
			connection.setListener(new HeadlessLoginPacketListener(this, connection, component -> {
				System.out.println(component.getString());
			}));
			connection.send(new ClientIntentionPacket(host, port, ConnectionProtocol.LOGIN));
			connection.send(new ServerboundHelloPacket(user.getGameProfile()));
			
			Executors.newScheduledThreadPool(2).scheduleAtFixedRate(() -> {
				connection.tick();
				// System.out.println("TICK: rec: " + connection.getAverageReceivedPackets() + " -> send: " +
				// connection.getAverageSentPackets());
				
			}, 50, 50, TimeUnit.MILLISECONDS);
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void run() {
		final int ticksToDo = timer.advanceTime(Util.getMillis());
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
		System.out.println("SHOULD TICK EVERSY 50 MS");
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
	
}
