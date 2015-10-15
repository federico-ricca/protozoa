package org.protozoa.pipeline.core;

import java.util.ArrayList;
import java.util.Collection;

public class PipelineNode {
	private DataUnit[] processedData;
	private Processor processor;
	private Collection<PipelineNode> initialNodes = new ArrayList<PipelineNode>();

	public PipelineNode(Processor _processor) {
		processor = _processor;
	}

	public PipelineNode connectTo(PipelineNode _node) {
		initialNodes.add(_node);
		return this;
	}

	public DataUnit[] getResults() {
		return processedData;
	}

	public void consume(DataUnit[] _data) {
		processedData = null;

		processedData = processor.process(_data);
		
		for (PipelineNode _node : initialNodes) {
			_node.consume(processedData);
		}

	}

}
