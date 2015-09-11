package org.protozoa.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ConfigurationHelper {

	/*
	 * Converts a comma separated list of values into a Collection of Strings
	 */
	public static Collection<String> parseAsStringCollection(String _string) {
		if (_string == null) {
			return new ArrayList<String>(0);
		}
		
		_string = _string.trim();
		
		if (_string.isEmpty()) {
			return new ArrayList<String>(0);
		}
		
		String[] _values = _string.split(",");

		for (int i = 0; i < _values.length; i++) {
			_values[i] = _values[i].trim();
		}

		return Arrays.asList(_values);
	}

	public static Collection<Long> parseAsLongCollection(String string) {
		return null;
	}

}
