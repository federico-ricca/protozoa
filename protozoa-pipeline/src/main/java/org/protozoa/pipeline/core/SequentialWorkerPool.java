package org.protozoa.pipeline.core;

public class SequentialWorkerPool implements WorkerPool {

	@Override
	public void start(int _size) {
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		_node.consume(_data);
	}

	@Override
	public void shutdown(long _timeout) {
	}

}
