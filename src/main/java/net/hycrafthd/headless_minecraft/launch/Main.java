package net.hycrafthd.headless_minecraft.launch;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.util.PathConverter;
import net.hycrafthd.minecraft_authenticator.login.User;
import net.hycrafthd.simple_minecraft_authenticator.creator.AuthenticationMethodCreator;
import net.hycrafthd.simple_minecraft_authenticator.util.AuthenticationMethodCreatorValueConverter;

public class Main {
	
	public static Logger LOGGER = LogManager.getLogger("Headless Minecraft Launch");
	
	public static void main(String[] args) throws IOException {
		final OptionParser parser = new OptionParser();
		
		// Default specs
		final OptionSpec<Void> helpSpec = parser.accepts("help", "Show the help menu").forHelp();
		
		final OptionSpec<AuthenticationMethodCreator> authMethodSpec = parser.accepts("auth-method", "Authentication method that should be used when file does not exists. Method 'console' and 'web' are always available").withRequiredArg().withValuesConvertedBy(new AuthenticationMethodCreatorValueConverter());
		final OptionSpec<Path> authFileSpec = parser.accepts("auth-file", "Authentication file to read and update. If file does not exist, or is not usable, then the user will be prompted to login with the selected authentication method.").withRequiredArg().withValuesConvertedBy(new PathConverter());
		final OptionSpec<Void> authHeadlessSpec = parser.accepts("auth-headless", "Force the authentication method to use a headless mode");
		
		final OptionSet set = parser.parse(args);
		
		if (set.has(helpSpec)) {
			parser.printHelpOn(IoBuilder.forLogger(LOGGER).setAutoFlush(true).setLevel(Level.ERROR).buildPrintStream());
			return;
		}
		
		final AuthenticationMethodCreator authMethod = set.valueOf(authMethodSpec);
		final Path authFile = set.valueOf(authFileSpec);
		final boolean authHeadless = set.has(authHeadlessSpec);
		
		Main.LOGGER.atInfo().log("Start Headless Minecraft");
		
		final Optional<User> user = MinecraftAuthenticator.authenticate(authMethod, authFile, authHeadless);
		
		System.out.println(user);
	}
	
}
