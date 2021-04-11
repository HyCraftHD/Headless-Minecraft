package net.hycrafthd.bot_event_system;
import java.lang.reflect.Method;
import java.util.List;

public class MethodsAndObject {
	
	private final Method method;
	private final Object object;
	
	public MethodsAndObject(Method method, Object object) {
		this.method = method;
		this.object = object;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public Object getObject() {
		return object;
	}
	
}
