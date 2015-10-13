package org.protozoa.pipeline.core;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomWorkerPool implements WorkerPool, Runnable {
	public static final int QUEUEING_RATIO = 1000;
	public static final int JOIN_INTERVAL = 5;

	private ArrayList<WorkerWrapper> wrapperPool;
	private Queue<Worker> workerQueue;

	private AtomicBoolean running = new AtomicBoolean(false);
	private Thread poolThread;

	private int id = 0;

	public CustomWorkerPool() {
		this(QUEUEING_RATIO);
	}

	public CustomWorkerPool(int _size) {
		workerQueue = new ArrayBlockingQueue<Worker>(_size);
	}

	@Override
	public void setSize(int _size) {
		wrapperPool = new ArrayList<WorkerWrapper>(_size);

		for (int i = 0; i < _size; i++) {
			wrapperPool.add(new WorkerWrapper());
		}
	}

	@Override
	public void submitWork(PipelineNode _node, DataUnit[] _data) {
		if (wrapperPool == null) {
			return;
		}

		if (!running.get()) {
			running.set(true);

			poolThread = new Thread(this);
			poolThread.start();
		}

		Worker _worker = new Worker(_node, _data);

		boolean _bSuccess = false;

		while (!_bSuccess) {
			try {
				workerQueue.add(_worker);
				_bSuccess = true;
			} catch (IllegalStateException _exception) {
				// queue full
			}
		}
	}

	private WorkerWrapper findAvailableWrapper() {
		WorkerWrapper _wrapper = null;

		for (WorkerWrapper _item : wrapperPool) {
			if (_item.lock()) {
				_wrapper = _item;
				break;
			}
		}

		return _wrapper;
	}

	@Override
	public void join(long _timeout) {
		while (!workerQueue.isEmpty()) {
			try {
				Thread.sleep(JOIN_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while (this.busyWrapperCount() > 0) {
			try {
				Thread.sleep(JOIN_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		running.set(false);
	}

	private int busyWrapperCount() {
		int _count = 0;

		for (WorkerWrapper _item : wrapperPool) {
			if (_item.isLocked()) {
				_count++;
			}
		}

		return _count;
	}

	@Override
	public void run() {
		while (running.get()) {
			Worker _worker = workerQueue.peek();

			if (_worker != null) {
				WorkerWrapper _wrapper = this.findAvailableWrapper();

				if (_wrapper != null) {
					workerQueue.remove(_worker);
					_wrapper.execute(_worker);
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
