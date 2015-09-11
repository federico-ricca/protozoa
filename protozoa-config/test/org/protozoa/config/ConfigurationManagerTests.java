package org.protozoa.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ConfigurationManagerTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	ConfigurationLoader emptyLoader = new ConfigurationLoader() {

		@Override
		public Configuration getConfiguration() throws ConfigurationException {
			return null;
		}
	};

	@Test(expected = ConfigurationException.class)
	public void noLoaderNoRemoteConfig() throws ConfigurationException {
		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				null, "", 0);

		_confMgr.load();
	}

	@Test
	public void customLoader() throws Exception {
		ConfigurationLoader _loader = new ConfigurationLoader() {

			@Override
			public Configuration getConfiguration() {
				Configuration _map = new Configuration();
				_map.put("aString", "aValue");
				_map.put("path/stringList", "aValue, anotherValue");

				return _map;
			}
		};

		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				_loader, "", 0);

		_confMgr.load();

		assertEquals("aValue", _confMgr.getString("aString"));
		assertTrue(_confMgr.getStringList("path/stringList").contains("aValue"));
		assertTrue(_confMgr.getStringList("path/stringList").contains(
				"anotherValue"));
		assertEquals(2, _confMgr.getStringList("path/stringList").size());
	}

	@Test(expected = ConfigurationException.class)
	public void remoteConfigFails() throws ConfigurationException {
		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				emptyLoader, "localhost:9597", 5000);

		_confMgr.load();
	}

	@Test
	public void remoteConfigEmpty() throws ConfigurationException, IOException {
		ConfigurationServer _configServer = new ConfigurationServer(emptyLoader);

		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				emptyLoader, "localhost:9595", 5000);

		try {
			_configServer.start(9595);

			_confMgr.load();
		} finally {
			_configServer.stop();
		}

		assertEquals(0, _confMgr.getConfiguration().size());
	}

	@Test
	public void remoteConfigLoaded() throws ConfigurationException, IOException {
		ConfigurationLoader _loader = new ConfigurationLoader() {

			@Override
			public Configuration getConfiguration()
					throws ConfigurationException {
				Configuration _conf = new Configuration();

				_conf.put("aString", "aValue");

				return _conf;
			}

		};

		ConfigurationServer _configServer = new ConfigurationServer(_loader);

		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				emptyLoader, "localhost:9596", 5000);

		try {
			_configServer.start(9596);

			_confMgr.load();
		} finally {
			_configServer.stop();
		}

		assertEquals("aValue", _confMgr.getString("aString"));
	}

	@Test
	public void loadLocalConfigurationFromFile() throws ConfigurationException,
			IOException {
		folder.create();

		File _aFile = folder.newFile();

		PrintStream _stream = new PrintStream(_aFile);

		_stream.println("aString = aValue");

		_stream.close();

		ConfigurationLoader _loader = new PropertiesFileConfigurationLoader(
				_aFile);

		CachedConfigurationManager _confMgr = new CachedConfigurationManager(
				_loader, null, 0);

		_confMgr.load();

		assertEquals("aValue", _confMgr.getString("aString"));
	}
}
