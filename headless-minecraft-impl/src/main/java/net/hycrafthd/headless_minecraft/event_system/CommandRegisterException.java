package net.hycrafthd.headless_minecraft.event_system;

public class CommandRegisterException extends RuntimeException{

	public CommandRegisterException(String message) {
		super(message);
	}
	
	public CommandRegisterException(String message, Throwable cause) {
		super(message, cause);
	}
}
