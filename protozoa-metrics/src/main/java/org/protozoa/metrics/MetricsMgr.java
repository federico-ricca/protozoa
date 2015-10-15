package org.protozoa.metrics;

public class MetricsMgr {
	private MetricsCollector collector;
	
	public MetricsMgr(MetricsCollector _collector) {
		collector = _collector;
	}
	
	public final Metric metric(String _name) {
		Metric _metric = new Metric(_name);
		
		collector.addMetric(_metric);
		
		return _metric;
	}
}
