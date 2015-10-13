package org.protozoa.pipeline.core;


public interface WorkerPool {

	public void setSize(int _size);

	public void submitWork(PipelineNode _node, DataUnit[] _data);

	public void join(long _timeout);
}
