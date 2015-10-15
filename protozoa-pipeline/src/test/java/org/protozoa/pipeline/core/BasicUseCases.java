package org.protozoa.pipeline.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class BasicUseCases {
	private static final int DATA_UNIT_COUNT = 20;

	private AtomicInteger counter = new AtomicInteger(0);

	class SimpleDataUnit implements DataUnit {
		String id;

		public SimpleDataUnit(int _a, int _b) {
			id = Integer.toString(_a) + "-" + Integer.toString(_b);
		}

		public String toString() {
			return id;
		}

	}

	@Test
	public void simplePipeline() {

		PipelineInput _input = new PipelineInput() {
			boolean _more = true;

			@Override
			public DataUnit[] fetch() {
				_more = false;
				DataUnit[] data = new DataUnit[DATA_UNIT_COUNT];

				for (int i = 0; i < DATA_UNIT_COUNT; i++) {
					data[i] = new DataUnit() {
					};
				}

				return data;
			}

			@Override
			public boolean available() {
				return _more;
			}
		};

		PipelineNode _filter = new PipelineNode(new Processor() {

			@Override
			public DataUnit[] process(DataUnit[] _source) {
				return _source;
			}

		});

		PipelineNode _emitter = new PipelineNode(new Processor() {

			@Override
			public DataUnit[] process(DataUnit[] _source) {
				DataUnit[] _result = Arrays.copyOfRange(_source, 0,
						_source.length - 3);

				return _result;
			}

		});

		PipelineNode _consumer = new PipelineNode(new Processor() {

			@Override
			public DataUnit[] process(DataUnit[] _source) {
				return _source;
			}

		});

		Pipeline _pipeline = new Pipeline();
		_pipeline.setInput(_input);
		_pipeline.addNode(_filter);
		_filter.connectTo(_emitter);
		_emitter.connectTo(_consumer);
		_pipeline.run();

		assertEquals(DATA_UNIT_COUNT - 3, _consumer.getResults().length);
	}

	@Test
	public void parallelPipeline() {
		Pipeline _pipeline = new Pipeline();

		PipelineInput _input = new PipelineInput() {
			int _more = DATA_UNIT_COUNT;

			@Override
			public DataUnit[] fetch() {
				DataUnit[] data = new DataUnit[DATA_UNIT_COUNT];

				for (int i = 0; i < DATA_UNIT_COUNT; i++) {
					data[i] = new SimpleDataUnit(_more, i);
				}

				return data;
			}

			@Override
			public boolean available() {
				return --_more >= 0;
			}
		};

		_pipeline.setInput(_input);

		Processor _p = new Processor() {

			@Override
			public DataUnit[] process(DataUnit[] _source) {
				for (int i = 0; i < _source.length; i++) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					counter.incrementAndGet();
				}

				return null;
			}

		};

		PipelineNode _filter1 = new PipelineNode(_p);
		PipelineNode _filter2 = new PipelineNode(_p);
		PipelineNode _filter3 = new PipelineNode(_p);
		PipelineNode _filter4 = new PipelineNode(_p);
		PipelineNode _filter5 = new PipelineNode(_p);

		_pipeline.addNode(_filter1);
		_pipeline.addNode(_filter2);
		_pipeline.addNode(_filter3);
		_pipeline.addNode(_filter4);
		_pipeline.addNode(_filter5);

		long _start = System.currentTimeMillis();
		_pipeline.start();

		try {
			_pipeline.shutdown(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long _elapsed = System.currentTimeMillis() - _start;

		System.out.println("elapsed = " + _elapsed);
		System.out.println("mean time = "
				+ (_elapsed / (DATA_UNIT_COUNT)));
		
		assertEquals(DATA_UNIT_COUNT * DATA_UNIT_COUNT, counter.get());
	}
}
