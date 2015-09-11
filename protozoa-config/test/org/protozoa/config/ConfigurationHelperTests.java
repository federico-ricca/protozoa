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
