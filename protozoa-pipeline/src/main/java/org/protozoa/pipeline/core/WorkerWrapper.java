package org.protozoa.pipeline.core;

import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerWrapper implements WorkerCallback {
	private AtomicBoolean locked;

	public WorkerWrapper() {
		locked = new AtomicBoolean(false);
	}

	public boolean lock() {
		/**
		 * negate so calling semantics make sense: 
		 * if (workerWrapper.lock()) doSomething
		 */
		return !locked.getAndSet(true);
	}

	public void signal() {
		locked.set(false);
	}

	public void execute(Worker _worker) {
		_worker.setCallback(this);

		Thread _t = new Thread(_worker);

		_t.start();
	}

	public boolean isLocked() {
		return locked.get();
	}
}
