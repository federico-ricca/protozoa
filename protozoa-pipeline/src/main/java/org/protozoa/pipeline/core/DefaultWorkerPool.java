package org.protozoa.pipeline.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DefaultWorkerPool implements WorkerPool {
	private ExecutorService executorService;

	@Override
	public void setSize(int _size) {
		executorService = Executors.newScheduledThreadPool(_size);
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		executorService.submit(new Worker(_node, _data));
	}

	@Override
	public void join(long _timeout) {
		executorService.shutdown();

		try {
			executorService.awaitTermination(_timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
