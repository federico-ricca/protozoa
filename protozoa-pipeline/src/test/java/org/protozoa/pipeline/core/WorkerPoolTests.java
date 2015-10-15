package org.protozoa.pipeline.core;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class WorkerPoolTests {
	public static final int DATA_SIZE = 100;
	public static final int CHUNK_SIZE = 20;

	class ElementCounter {
		private AtomicInteger count = new AtomicInteger(0);

		public final void inc() {
			count.incrementAndGet();
		}

		public int getElementCount() {
			return count.get();
		}
	}

	@Test
	public void testWorkerPool() {
		final ElementCounter _counter = new ElementCounter();

		//WorkerPool _pool = new CustomWorkerPool(-1);
		WorkerPool _pool = new SequentialWorkerPool();

		_pool.start(10);
		
		for (int i = 0; i < DATA_SIZE; i++) {
			PipelineNode _node = new PipelineNode(new Processor() {

				@Override
				public DataUnit[] process(DataUnit[] _source) {
					for (DataUnit _item : _source) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						_counter.inc();
					}
					return null;
				}
			});

			DataUnit[] _data = new DataUnit[CHUNK_SIZE];
			_pool.submitWork(_node, _data);
		}

		_pool.shutdown(6000);

		assertEquals(DATA_SIZE * CHUNK_SIZE, _counter.getElementCount());
	}
}
