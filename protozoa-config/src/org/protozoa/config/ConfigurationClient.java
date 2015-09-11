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

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConfigurationClient {

	public Configuration fetchConfiguration(String _host, int _iPort, int _iConnectTimeout)
			throws Exception {
		Configuration _conf = null;

		Socket _socket = new Socket();
		_socket.connect(new InetSocketAddress(_host, _iPort), _iConnectTimeout);

		try {
			ObjectOutputStream _objectOutputStream = new ObjectOutputStream(
					_socket.getOutputStream());

			_objectOutputStream
					.writeObject(ConfigurationServer.FETCH_CONFIGURATION);

			ObjectInputStream _objectInputStream = new ObjectInputStream(
					_socket.getInputStream());

			String _serializedConfig = (String) _objectInputStream.readObject();

			_conf = new ConfigurationParser()
					.parseNetworkSerializedConfiguration(_serializedConfig);

			_objectInputStream.close();
			_objectOutputStream.close();
		} finally {
			_socket.close();
		}

		return _conf;
	}

}
