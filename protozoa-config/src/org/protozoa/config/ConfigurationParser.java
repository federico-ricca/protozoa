package org.protozoa.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class ConfigurationParser {
	public final Configuration parseConfiguration(File _configFile)
			throws IOException {
		return this.parseConfiguration(new FileReader(_configFile));
	}

	public final Configuration parseConfiguration(Reader _reader)
			throws IOException {
		Configuration _conf = new Configuration();

		BufferedReader _bufferedReader = new BufferedReader(_reader);

		String _line = _bufferedReader.readLine();

		while (_line != null) {
			String[] _keyValue = _line.split("=");

			_conf.put(_keyValue[0].trim(), _keyValue[1].trim());

			_line = _bufferedReader.readLine();
		}
		return _conf;
	}

	public final Configuration parseNetworkSerializedConfiguration(
			String _config) {
		Configuration _conf = new Configuration();

		Scanner _scanner = new Scanner(_config);
		_scanner.useDelimiter("\n");

		while (_scanner.hasNextLine()) {
			String _line = _scanner.nextLine();

			String[] _keyValue = _line.split("=");

			_conf.put(_keyValue[0].trim(), _keyValue[1].trim());
		}

		_scanner.close();

		return _conf;
	}
}
