package org.protozoa.metrics;

import java.util.HashMap;

public class LocalMetricsCollector implements MetricsCollector {
	private HashMap<String, Metric> metrics = new HashMap<String, Metric>();

	@Override
	public void addMetric(Metric _metric) {
		metrics.put(_metric.getName(), _metric);
	}

	@Override
	public Metric getMetric(String _name) {
		return metrics.get(_name);
	}

}
