package net.hycrafthd.headless_minecraft.event_system;

public class EventException extends RuntimeException{

	public EventException(String message) {
		super(message);
	}
	
	public EventException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
