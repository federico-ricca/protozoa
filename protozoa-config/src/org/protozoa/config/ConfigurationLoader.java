package org.protozoa.config;

public interface ConfigurationLoader {
	public Configuration getConfiguration() throws ConfigurationException;
}
