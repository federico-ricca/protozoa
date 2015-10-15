package org.protozoa.pipeline.core;

public class WorkUnit {

	private PipelineNode node;
	private DataUnit[] data;

	public WorkUnit(PipelineNode _node, DataUnit[] _data) {
		node = _node;
		data = _data;
	}

	public final PipelineNode getNode() {
		return node;
	}

	public final DataUnit[] getData() {
		return data;
	}
}
