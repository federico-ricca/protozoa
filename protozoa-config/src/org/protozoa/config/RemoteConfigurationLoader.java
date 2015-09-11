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

public class RemoteConfigurationLoader implements ConfigurationLoader {
	private String host;
	private int iPort;
	private int iConnectTimeout;

	public RemoteConfigurationLoader(String _endpoint, int _iConnectTimeout)
			throws ConfigurationException {
		String[] _split = _endpoint.split(":");

		try {
			host = _split[0];
			iPort = Integer.valueOf(_split[1]);
			iConnectTimeout = _iConnectTimeout;
		} catch (Exception _ex) {
			throw new ConfigurationException(
					"Invalid enpoint for remote configuration: " + _endpoint
							+ " - Format is <hostname>:<port>");
		}
	}

	@Override
	public Configuration getConfiguration() throws ConfigurationException {
		Configuration _conf = null;

		try {
			_conf = new ConfigurationClient().fetchConfiguration(host, iPort,
					iConnectTimeout);
		} catch (Exception _exception) {
			throw new ConfigurationException(
					"Cannot fetch remote configuration: " + _exception);
		}
		return _conf;
	}

}
