package org.protozoa.config;

import java.io.IOException;

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
