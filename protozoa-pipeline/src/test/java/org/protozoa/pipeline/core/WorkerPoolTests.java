package org.protozoa.pipeline.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WorkerPoolTests {
	public static final int DATA_SIZE = 10;

	class ElementCounter {
		private int count = 0;

		public final void inc() {
			count++;
		}

		public int getElementCount() {
			return count;
		}
	}

	@Test
	public void testWorkerPool() {
		final ElementCounter _counter = new ElementCounter();

		WorkerPool _pool = new CustomWorkerPool(50000);
		//WorkerPool _pool = new SequentialWorkerPool();

		_pool.setSize(50);

		long _start = System.currentTimeMillis();
		
		for (int i = 0; i < DATA_SIZE; i++) {
			PipelineNode _node = new PipelineNode(new Processor() {

				@Override
				public DataUnit[] process(DataUnit[] _source) {
					for (DataUnit _item : _source) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						_counter.inc();
					}
					return null;
				}
			});

			DataUnit[] _data = new DataUnit[DATA_SIZE];
			_pool.submitWork(_node, _data);
		}

		_pool.join(60000);

		System.out.println("Elapsed: " + (System.currentTimeMillis()-_start));

		assertEquals(DATA_SIZE * DATA_SIZE, _counter.getElementCount());
	}
}
