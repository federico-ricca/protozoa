package org.protozoa.pipeline.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DefaultWorkerPool implements WorkerPool {
	private ExecutorService executorService;

	class NodeWorker implements Runnable {
		private PipelineNode node;
		private DataUnit[] data;

		public NodeWorker(PipelineNode _node, DataUnit[] _data) {
			node = _node;
			data = _data;
		}

		@Override
		public void run() {
			node.consume(data);
		}

	}

	@Override
	public void start(int _size) {
		executorService = Executors.newScheduledThreadPool(_size);
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		executorService.submit(new NodeWorker(_node, _data));
	}

	@Override
	public void shutdown(long _timeout) {
		executorService.shutdown();

		try {
			executorService.awaitTermination(_timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
