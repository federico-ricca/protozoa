package org.protozoa.pipeline.core;


public interface PipelineInput {

	public boolean available();

	public DataUnit[] fetch();
}
