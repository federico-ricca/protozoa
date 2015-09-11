package org.protozoa.config;

import java.util.HashMap;

public class Configuration extends HashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3237067520710997962L;

	public String toString() {
		StringBuilder _configStringBuilder = new StringBuilder();

		for (String _key : this.keySet()) {
			_configStringBuilder.append(_key);
			_configStringBuilder.append(" = ");
			_configStringBuilder.append(this.get(_key));
			_configStringBuilder.append("\n");
		}

		return _configStringBuilder.toString();
	}

}
