package org.protozoa.pipeline.core;

public class SequentialWorkerPool implements WorkerPool {

	@Override
	public void setSize(int _size) {
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		new Worker(_node, _data).run();
	}

	@Override
	public void join(long _timeout) {
	}

}
