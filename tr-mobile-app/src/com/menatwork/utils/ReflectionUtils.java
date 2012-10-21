package com.menatwork.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {

	public static boolean isGetter(final Method method) {
		final String methodName = method.getName();
		return methodName.startsWith("get") || methodName.startsWith("is");
	}

	public static boolean isSetter(final Method method) {
		final String methodName = method.getName();
		return methodName.startsWith("set");
	}

	public static Method setterForGetter(final Method getter,
			final Class<?> type) {
		final String nameWithoutPrefix = getGetterNameWithoutPrefix(getter);

		final Method[] allMethods = type.getDeclaredMethods();
		for (final Method method : allMethods)
			if (method.getName().equals("set" + nameWithoutPrefix))
				return method;
		throw new RuntimeException("No setter found for " + getter.getName());
	}

	private static String getGetterNameWithoutPrefix(final Method getter) {
		final String fullName = getter.getName();
		if (fullName.startsWith("get"))
			return fullName.replaceFirst("get", "");
		if (fullName.startsWith("is"))
			return fullName.replaceFirst("is", "");
		return fullName;
	}

}
