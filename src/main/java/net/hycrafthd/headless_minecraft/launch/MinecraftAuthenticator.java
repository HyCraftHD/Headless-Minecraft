package net.hycrafthd.headless_minecraft.launch;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import net.hycrafthd.minecraft_authenticator.login.AuthenticationException;
import net.hycrafthd.minecraft_authenticator.login.AuthenticationFile;
import net.hycrafthd.minecraft_authenticator.login.Authenticator;
import net.hycrafthd.minecraft_authenticator.login.User;
import net.hycrafthd.simple_minecraft_authenticator.creator.AuthenticationMethodCreator;
import net.hycrafthd.simple_minecraft_authenticator.util.SimpleAuthenticationFileUtil;
import net.hycrafthd.simple_minecraft_authenticator.util.SimpleAuthenticationFileUtil.AuthenticationData;

public class MinecraftAuthenticator {
	
	public static Optional<User> authenticate(AuthenticationMethodCreator authMethod, Path authFile, boolean authHeadless) {
		Main.LOGGER.atInfo().log("Run minecraft account authentication");
		
		if (authMethod == null && authFile == null) {
			Main.LOGGER.atInfo().log("Skip authentication. Launch in offline mode");
			return Optional.empty();
		}
		
		final PrintStream out = IoBuilder.forLogger(Main.LOGGER).setAutoFlush(true).setLevel(Level.INFO).buildPrintStream();
		
		User user = authenticationWithFile(out, authFile, authHeadless);
		if (user != null) {
			return Optional.of(user);
		} else {
			user = authenticationWithInteraction(out, authMethod, authFile, authHeadless);
		}
		
		if (user == null) {
			Main.LOGGER.atInfo().log("Authentication failed. Launch in offline mode");
		}
		
		return Optional.ofNullable(user);
	}
	
	private static User authenticationWithFile(PrintStream out, Path authFile, boolean authHeadless) {
		if (!Files.isRegularFile(authFile)) {
			return null;
		}
		
		try {
			final byte[] bytes = Files.readAllBytes(authFile);
			final AuthenticationData authenticationData = SimpleAuthenticationFileUtil.read(bytes);
			final Authenticator authenticator = authenticationData.creator().create(authHeadless, out, System.in) //
					.existingAuthentication(authenticationData.file()) //
					.buildAuthenticator();
			
			return runAuthentication(authenticator, authenticationData.creator(), authFile);
		} catch (final IOException ex) {
			Main.LOGGER.atWarn().log("Cannot read auth file because {}", ex.getMessage());
			return null;
		}
	}
	
	private static User authenticationWithInteraction(PrintStream out, AuthenticationMethodCreator authMethod, Path authFile, boolean authHeadless) {
		try {
			final Authenticator authenticator = authMethod.create(authHeadless, out, System.in) //
					.initalAuthentication() //
					.buildAuthenticator();
			
			return runAuthentication(authenticator, authMethod, authFile);
		} catch (final AuthenticationException ex) {
			Main.LOGGER.atError().withThrowable(ex).log("Cannot authenticate");
			return null;
		}
	}
	
	private static User runAuthentication(Authenticator authenticator, AuthenticationMethodCreator authMethod, Path authFile) {
		try {
			authenticator.run();
			
			saveAuthenticationFile(authenticator.getResultFile(), authMethod, authFile);
			return authenticator.getUser().get();
		} catch (final AuthenticationException ex) {
			Main.LOGGER.atWarn().log("Cannot run authentication because {}", ex.getMessage());
			saveAuthenticationFile(authenticator.getResultFile(), authMethod, authFile);
			return null;
		}
	}
	
	private static void saveAuthenticationFile(AuthenticationFile resultFile, AuthenticationMethodCreator authMethod, Path authFile) {
		if (resultFile != null) {
			try {
				final byte[] bytes = SimpleAuthenticationFileUtil.write(new AuthenticationData(resultFile, authMethod));
				Files.write(authFile, bytes);
			} catch (final IOException ex) {
				Main.LOGGER.atWarn().log("Cannot save updated auth file because {}", ex.getMessage());
			}
		}
	}
}
