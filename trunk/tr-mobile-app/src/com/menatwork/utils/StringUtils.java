package com.menatwork.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {

	public static String concatStringsWithSep(final List<String> strings, final String separator) {
		final StringBuilder sb = new StringBuilder();
		String sep = "";
		for (final String s : strings) {
			sb.append(sep).append(s);
			sep = separator;
		}
		return sb.toString();
	}

	public static String[] removeEmptyStrings(final String... strings) {
		final List<String> withoutEmpties = removeEmptyStrings(Arrays.asList(strings));
		return withoutEmpties.toArray(new String[0]);
	}

	public static List<String> removeEmptyStrings(final List<String> strings) {
		final LinkedList<String> withoutEmpties = new LinkedList<String>();

		for (final String string : strings)
			if (!"".equals(string))
				withoutEmpties.add(string);

		return withoutEmpties;
	}

}
