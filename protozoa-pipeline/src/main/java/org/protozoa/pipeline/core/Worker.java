package org.protozoa.pipeline.core;

class Worker implements Runnable {
	private PipelineNode node;
	private DataUnit[] data;
	private WorkerCallback callback;

	public Worker(PipelineNode _node, DataUnit[] _data) {
		node = _node;
		data = _data;
	}

	public void setCallback(WorkerCallback _callback) {
		callback = _callback;
	}

	@Override
	public void run() {
		node.consume(data);

		if (callback != null) {
			callback.signal();
		}
	}
}