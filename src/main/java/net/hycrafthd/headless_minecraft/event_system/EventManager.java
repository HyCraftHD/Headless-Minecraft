package net.hycrafthd.headless_minecraft.event_system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
	
	/**
	 * Key: EventType Value: Method with object
	 */
	private Map<Class<?>, List<MethodsAndObject>> listenerMethodLists;
	
	public EventManager() {
		listenerMethodLists = new HashMap<>();
	}
	
	public void registerListener(Object listener) {
		if (listener == null) {
			return;
		}
		
		Class<?> clazz = listener.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(EventHandler.class)) {
				continue;
			}
			if (method.getParameterCount() < 1) {
				throw new EventException("Missing event parameter!");
			}
			if (method.getParameterCount() > 1) {
				throw new EventException("Too many parameters!");
			}
			Class<?> parameter = method.getParameterTypes()[0];
			if (!Event.class.isAssignableFrom(parameter)) {
				throw new EventException("Parameter type missmatch! Found: " + parameter.getName());
			}
			
			if (listenerMethodLists.containsKey(parameter)) {
				listenerMethodLists.get(parameter).add(new MethodsAndObject(method, listener));
			} else {
				List<MethodsAndObject> temp = new ArrayList<MethodsAndObject>();
				temp.add(new MethodsAndObject(method, listener));
				listenerMethodLists.put(parameter, temp);
			}
		}
	}
	
	public void executeEvents(Event event) {
		if (!listenerMethodLists.containsKey(event.getClass())) {
			return;
		}
		
		listenerMethodLists.get(event.getClass()).forEach(e -> {
			try {
				e.getMethod().invoke(e.getObject(), event);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				throw new EventException("Event invoke Exception", ex);
			}
		});
	}
	
}
