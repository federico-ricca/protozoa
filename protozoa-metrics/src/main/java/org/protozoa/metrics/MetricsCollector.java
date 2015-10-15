package org.protozoa.metrics;

public interface MetricsCollector {
	public void addMetric(Metric _metric);

	public Metric getMetric(String _name);
}
