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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigurationServer {
	public static final String FETCH_CONFIGURATION = "fetch-configuration";
	public static final int NUM_WORKERS = 5;

	private ConfigurationLoader loader;
	private String cachedConfiguration;
	private volatile boolean bServerRunning = false;

	public class ConfigurationServerWorker implements Runnable {
		private Socket socket;

		public ConfigurationServerWorker(Socket _socket) {
			socket = _socket;
		}

		@Override
		public void run() {
			ObjectInputStream _objectInputStream = null;
			ObjectOutputStream _objectOutputStream = null;
			try {
				_objectInputStream = new ObjectInputStream(
						socket.getInputStream());

				String _message = (String) _objectInputStream.readObject();

				if (_message.equals(FETCH_CONFIGURATION)) {
					_objectOutputStream = new ObjectOutputStream(
							socket.getOutputStream());

					_objectOutputStream.writeObject(cachedConfiguration);

					_objectOutputStream.close();
				}

				_objectInputStream.close();
			} catch (Exception _exception) {
				_exception.printStackTrace();
			} finally {
				try {
					if (_objectOutputStream != null) {
						_objectOutputStream.close();
					}
					if (_objectInputStream != null) {
						_objectInputStream.close();
					}
				} catch (Exception _exception) {
					_exception.printStackTrace();
				}
			}
		}

	}

	public class ConfigurationServerMainThread implements Runnable {
		private int iPort;

		public ConfigurationServerMainThread(int _iPort) {
			iPort = _iPort;
		}

		@Override
		public void run() {
			ExecutorService _service = Executors
					.newFixedThreadPool(NUM_WORKERS);

			ServerSocket _serverSocket;
			try {
				_serverSocket = new ServerSocket(iPort);

				bServerRunning = true;

				while (bServerRunning) {
					Socket _socket = _serverSocket.accept();

					_service.submit(new ConfigurationServerWorker(_socket));
				}

				_serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			_service.shutdown();
		}
	}

	public ConfigurationServer(ConfigurationLoader _loader) {
		loader = _loader;
		cachedConfiguration = "";
	}

	public void start(int _iPort) throws IOException, ConfigurationException {
		Configuration _configuration = loader.getConfiguration();

		if (_configuration != null) {
			cachedConfiguration = _configuration.toString();
		}

		ExecutorService _service = Executors.newFixedThreadPool(1);

		_service.submit(new ConfigurationServerMainThread(_iPort));
		
		
	}

	public void stop() {
		bServerRunning = false;
	}
}
