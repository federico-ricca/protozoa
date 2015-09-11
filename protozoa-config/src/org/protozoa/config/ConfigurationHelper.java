/*************************************************************************** 
   Copyright 2015 Federico Ricca

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ***************************************************************************/
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
