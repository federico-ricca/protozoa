package org.protozoa.config;

import java.io.File;
import java.io.IOException;

public class PropertiesFileConfigurationLoader implements ConfigurationLoader {

	private File configurationFile;

	public PropertiesFileConfigurationLoader(String _configFilename) {
		this(new File(_configFilename));
	}

	public PropertiesFileConfigurationLoader(File _configFile) {
		configurationFile = _configFile;
	}

	@Override
	public Configuration getConfiguration() throws ConfigurationException {
		Configuration _conf = null;

		try {
			_conf = new ConfigurationParser()
					.parseConfiguration(configurationFile);
		} catch (IOException _ioException) {
			throw new ConfigurationException(_ioException.toString());
		}

		return _conf;
	}

}
