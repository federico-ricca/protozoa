package org.protozoa.pipeline.core;

public interface Processor {
	public DataUnit[] process(DataUnit[] _source);
}
