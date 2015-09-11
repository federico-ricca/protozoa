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
