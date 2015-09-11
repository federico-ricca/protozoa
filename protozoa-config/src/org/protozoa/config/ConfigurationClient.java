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
