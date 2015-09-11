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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class ConfigurationHelperTests {

	@Test
	public void parseAsStringCollection() {
		String list = "one,two, three  , four";

		Collection<String> _collection = ConfigurationHelper
				.parseAsStringCollection(list);

		assertTrue(_collection.contains("one"));
		assertTrue(_collection.contains("two"));
		assertTrue(_collection.contains("three"));
		assertTrue(_collection.contains("four"));
	}
	
	@Test
	public void parseAsStringCollectionEmptyString() {
		String list = "   ";
		
		Collection<String> _collection = ConfigurationHelper
				.parseAsStringCollection(list);

		assertEquals(0, _collection.size());
	}
	
	@Test
	public void parseAsStringCollectionNullInput() {
		String list = null;
		
		Collection<String> _collection = ConfigurationHelper
				.parseAsStringCollection(list);

		assertEquals(0, _collection.size());
	}
}
