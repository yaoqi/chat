package com.mercury.chat.common.test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Lists;

public class AssertUtils {

	public static <T> void assertEquals(T expected, T Actual, String... ignoreFields) {
		if (expected == Actual) {
			return;
		}
		if (expected == null) {
			throw new AssertionError("Expected Object is null, but Actual Object is not null");
		} else if (Actual == null) {
			throw new AssertionError("Expected Object is not null, but Actual Object is null");
		} if (expected.equals(Actual)) {
			return;
		}
		assertFields(expected, Actual, (ignoreFields == null ? Lists.<String>newArrayList() : Lists.newArrayList(ignoreFields)));
	}
	
	protected static <T> void assertFields(T expected, T Actual, Collection<String> ignoreFields) {
		Class clazz = expected.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Class fieldClass = null;
		String fieldName = null;
		for (Field field : fields) {
			fieldName = field.getName();
			if (ignoreFields.contains(fieldName)) {
				continue;
			}
			try {
				field.setAccessible(true);
				Object expectedObj = field.get(expected);
				Object actualObj = field.get(Actual);
				fieldClass = field.getType();
				if (isJavaClass(fieldClass) && isSimpleType(fieldClass)) {
					if (fieldClass == BigDecimal.class && ObjectUtils.compare((BigDecimal)expected, (BigDecimal)Actual) != 0) {
						throw new AssertionError( fieldName + " expecte to be " + expectedObj + ", but actual value is " + actualObj);
					}
					if (!ObjectUtils.equals(expectedObj, actualObj)) {
						throw new AssertionError( fieldName + " expecte to be " + expectedObj + ", but actual value is " + actualObj);
					}
				} else if (Collection.class.isAssignableFrom(fieldClass)) {
//					Type fc = field.getGenericType();
//					if (fc == null) continue; 
//					if (fc instanceof ParameterizedType) {
//						ParameterizedType pt = (ParameterizedType) fc;
//						Class genericClazz = (Class)pt.getActualTypeArguments()[0];
//					}
					//TODO
				} else if (Map.class.isAssignableFrom(fieldClass)	) {
					//TODO
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean isJavaClass(Class<?> clazz) {     
	    return clazz != null && clazz.getClassLoader() == null;     
	}
	private static boolean isSimpleType(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.getName().startsWith("java.lang");
	}
}
