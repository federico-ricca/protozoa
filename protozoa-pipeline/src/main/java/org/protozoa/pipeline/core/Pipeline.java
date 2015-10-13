package org.protozoa.pipeline.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Pipeline {
	private PipelineInput input;
	private Collection<PipelineNode> initialNodes = new ArrayList<PipelineNode>();
	private WorkerPool workerPool;

	public void setInput(PipelineInput _input) {
		input = _input;
	}

	public void start() {
		workerPool = new DefaultWorkerPool();

		workerPool.setSize(initialNodes.size());

		while (input.available()) {
			DataUnit[] _data = input.fetch();

			int _index = 0;
			int _partitionSize = _data.length / initialNodes.size();
			System.out.println("partition size=" + _partitionSize);

			for (PipelineNode _node : initialNodes) {
				DataUnit[] _tempData = Arrays
						.copyOfRange(
								_data,
								_index,
								_index
										+ Math.max((_data.length - _index)
												% (2 * _partitionSize),
												_partitionSize));

				_index += _partitionSize;

				System.out.println("submitting worker " + _tempData.length);
				workerPool.submitWork(_node, _tempData);
			}
		}
	}

	public void run() {
		while (input.available()) {
			DataUnit[] _data = input.fetch();

			int _index = 0;
			int _partitionSize = _data.length / initialNodes.size();

			for (PipelineNode _node : initialNodes) {
				DataUnit[] _tempData = Arrays
						.copyOfRange(
								_data,
								_index,
								_index
										+ Math.max((_data.length - _index)
												% (2 * _partitionSize),
												_partitionSize));

				_index += _partitionSize;

				_node.consume(_tempData);
			}
		}
	}

	public void addNode(PipelineNode _node) {
		initialNodes.add(_node);
	}

	public void join(long _waitTime) throws InterruptedException {
		if (workerPool == null) {
			return;
		}

		workerPool.join(_waitTime);
	}

}
