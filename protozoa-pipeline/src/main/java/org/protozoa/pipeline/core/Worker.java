package org.protozoa.pipeline.core;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class Worker implements Runnable {
	private AtomicBoolean locked;
	private AtomicBoolean running;
	private AtomicReference<WorkUnit> unit;

	public Worker() {
		locked = new AtomicBoolean(false);
		running = new AtomicBoolean(false);
		unit = new AtomicReference<WorkUnit>(null);
	}

	public void process(WorkUnit _workUnit) {
		unit.compareAndSet(null, _workUnit);
	}

	public boolean isLocked() {
		return locked.get();
	}

	public boolean lock() {
		return locked.compareAndSet(false, true);
	}

	@Override
	public void run() {
		running.set(true);
		
		while (running.get()) {

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (unit.get() != null) {
				PipelineNode _node = unit.get().getNode();
				DataUnit[] _data = unit.get().getData();

				_node.consume(_data);

				unit.set(null);
				locked.set(false);
			}
		}
	}

	public void shutdown() {
		while (this.isLocked()) {
		}
		
		running.set(false);
	}
}