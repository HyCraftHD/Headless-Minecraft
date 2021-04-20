package net.hycrafthd.headless_minecraft.util;

import java.util.Enumeration;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {
	
	public static <T> Stream<T> toStream(Enumeration<T> enumeration) {
		return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED) {
			
			@Override
			public boolean tryAdvance(Consumer<? super T> consumer) {
				final boolean moreElements = enumeration.hasMoreElements();
				if (moreElements) {
					consumer.accept(enumeration.nextElement());
				}
				return moreElements;
			}
			
			@Override
			public void forEachRemaining(Consumer<? super T> consumer) {
				while (enumeration.hasMoreElements()) {
					consumer.accept(enumeration.nextElement());
				}
			}
		}, false);
	}
	
}
