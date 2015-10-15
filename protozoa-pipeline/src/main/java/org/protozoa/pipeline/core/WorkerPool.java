package org.protozoa.pipeline.core;


public interface WorkerPool {

	public void start(int _size);
	
	public void submitWork(PipelineNode _node, DataUnit[] _data);

	public void shutdown(long _timeout);
}
