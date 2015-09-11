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
