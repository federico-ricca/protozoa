package org.protozoa.config;

import java.util.Collection;

public interface ConfigurationManager {

	public void load() throws Exception;
	
	public Collection<String> getStringList(String _path);

	public Collection<Long> getLongList(String _path);

	public int getInt(String _path);
	
	public long getLong(String _path);
	
	public String getString(String _path);
}
