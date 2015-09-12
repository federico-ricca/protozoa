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
	public final Collection<String> getStringList(String _path) {
		return ConfigurationHelper.parseAsStringCollection(configuration
				.get(_path));
	}

	@Override
	public final Collection<Long> getLongList(String _path) {
		return ConfigurationHelper.parseAsLongCollection(configuration
				.get(_path));
	}

	@Override
	public final int getInt(String _path) {
		return Integer.parseInt(configuration.get(_path));
	}

	@Override
	public final long getLong(String _path) {
		return Long.parseLong(configuration.get(_path));
	}

	@Override
	public final String getString(String _path) {
		return configuration.get(_path);
	}

	public final Configuration getConfiguration() {
		return configuration;
	}
}
