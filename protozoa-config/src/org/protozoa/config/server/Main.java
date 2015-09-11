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
package org.protozoa.config.server;

import java.io.IOException;

import org.protozoa.config.Configuration;
import org.protozoa.config.ConfigurationException;
import org.protozoa.config.ConfigurationLoader;
import org.protozoa.config.ConfigurationServer;

public class Main {
	private final int iPort = 9595;

	public static void main(String[] _params) {

		try {
			new Main().run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() throws IOException, ConfigurationException {
		ConfigurationLoader _loader = new ConfigurationLoader() {

			@Override
			public Configuration getConfiguration()
					throws ConfigurationException {
				Configuration _conf = new Configuration();

				_conf.put("/pulloverApp/services/packages", "com.example");

				return _conf;
			}

		};

		ConfigurationServer _configServer = new ConfigurationServer(_loader);

		System.out.println("Starting server on port " + iPort);
		_configServer.start(iPort);
	}
}
