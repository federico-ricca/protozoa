package org.protozoa.metrics;

public class Metric {
	private String name;
	private long accumulatedTime = 0;
	private long elapsedTime = 0;
	private long startTime = 0;
	
	public Metric(String _name) {
		name = _name;
	}

	public final void start() {
		startTime = System.currentTimeMillis();
	}

	public final void stop() {
		elapsedTime = System.currentTimeMillis() - startTime;
		accumulatedTime += elapsedTime;
	}

	public final String getName() {
		return name;
	}

	public long getTotalAccumulatedTime() {
		return accumulatedTime;
	}

}
