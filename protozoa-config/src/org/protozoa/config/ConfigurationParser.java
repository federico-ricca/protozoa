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
