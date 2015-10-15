package org.protozoa.pipeline.core;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomWorkerPool implements WorkerPool, Runnable {
	public static final int DEFAULT_QUEUE_SIZE = 1000;
	public static final int JOIN_INTERVAL = 5;

	private ArrayList<Worker> workerPool;
	private Queue<WorkUnit> workUnitQueue;

	private AtomicBoolean running = new AtomicBoolean(false);
	private AtomicBoolean shuttingDown = new AtomicBoolean(false);

	private Thread poolThread;
	private int queueSize;

	public CustomWorkerPool(int _queueSize) {
		if (_queueSize < 1) {
			_queueSize = DEFAULT_QUEUE_SIZE;
		}
		
		queueSize = _queueSize;
	}

	@Override
	public void start(int _size) {
		workUnitQueue = new ArrayBlockingQueue<WorkUnit>(queueSize);

		workerPool = new ArrayList<Worker>(_size);

		for (int i = 0; i < _size; i++) {
			Worker _worker = new Worker();
			workerPool.add(_worker);

			new Thread(_worker).start();
		}

		running.set(true);

		poolThread = new Thread(this);
		poolThread.start();
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		if (shuttingDown.get() || workerPool == null) {
			return;
		}

		WorkUnit _workUnit = new WorkUnit(_node, _data);

		boolean _bSuccess = false;

		while (!_bSuccess) {
			try {
				workUnitQueue.add(_workUnit);
				_bSuccess = true;
			} catch (IllegalStateException _exception) {
				// queue full
			}
		}
	}

	private Worker findAvailableWorker() {
		Worker _worker = null;

		for (Worker _item : workerPool) {
			if (_item.lock()) {
				_worker = _item;
				break;
			}
		}

		return _worker;
	}

	@Override
	public void shutdown(long _timeout) {
		shuttingDown.set(true);

		while (!workUnitQueue.isEmpty()) {
			try {
				Thread.sleep(JOIN_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Worker _worker : workerPool) {
			_worker.shutdown();
		}

		running.set(false);
	}

	private int busyWrapperCount() {
		int _count = 0;

		for (Worker _item : workerPool) {
			if (_item.isLocked()) {
				_count++;
			}
		}

		return _count;
	}

	@Override
	public void run() {
		while (running.get()) {
			WorkUnit _workUnit = workUnitQueue.peek();

			if (_workUnit != null) {
				Worker _worker = this.findAvailableWorker();

				if (_worker != null) {
					workUnitQueue.remove(_workUnit);
					_worker.process(_workUnit);
				}
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
