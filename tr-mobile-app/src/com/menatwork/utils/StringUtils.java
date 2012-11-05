package com.menatwork.utils;

import java.util.List;

public class StringUtils {

	public static String concatStringsWithSep(final List<String> strings,
			final String separator) {
		final StringBuilder sb = new StringBuilder();
		String sep = "";
		for (final String s : strings) {
			sb.append(sep).append(s);
			sep = separator;
		}
		return sb.toString();
	}

}
