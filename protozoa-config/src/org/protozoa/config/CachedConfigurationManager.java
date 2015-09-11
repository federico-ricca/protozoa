package org.protozoa.config;

import java.util.Collection;

public class CachedConfigurationManager implements ConfigurationManager {
	private Configuration configuration;
	private ConfigurationLoader loader;
	private RemoteConfigurationLoader remoteLoader;

	public CachedConfigurationManager(ConfigurationLoader _loader,
			String _endpoint, int _iConnectTimeout)
			throws ConfigurationException {
		configuration = null;
		loader = _loader;

		if (_endpoint != null && _endpoint.trim().length() > 0) {
			remoteLoader = new RemoteConfigurationLoader(_endpoint,
					_iConnectTimeout);
		}
	}

	@Override
	public void load() throws ConfigurationException {
		if (loader != null) {
			configuration = loader.getConfiguration();
		}

		if (configuration == null) {
			// load from remote
			if (remoteLoader != null) {
				configuration = remoteLoader.getConfiguration();
			}

			if (configuration == null) {
				throw new ConfigurationException(
						"No local or remote configuration");
			}
		}
	}

	@Override
	public Collection<String> getStringList(String _path) {
		return ConfigurationHelper.parseAsStringCollection(configuration
				.get(_path));
	}

	@Override
	public Collection<Long> getLongList(String _path) {
		return ConfigurationHelper.parseAsLongCollection(configuration
				.get(_path));
	}

	@Override
	public int getInt(String _path) {
		return Integer.parseInt(configuration.get(_path));
	}

	@Override
	public long getLong(String _path) {
		return Long.parseLong(configuration.get(_path));
	}

	@Override
	public String getString(String _path) {
		return configuration.get(_path);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

}
