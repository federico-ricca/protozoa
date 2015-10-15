package org.protozoa.metrics;

import static org.junit.Assert.*;

import org.junit.Test;

public class UseCases {

	@Test
	public void testSimpleMetric() {
		final String METRIC_NAME = "metric.tag";
		final long MILLIS = 300;
		
		LocalMetricsCollector _collector = new LocalMetricsCollector();

		MetricsMgr metrics = new MetricsMgr(_collector);

		Metric m = metrics.metric(METRIC_NAME);

		m.start();

		try {
			Thread.sleep(MILLIS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m.stop();

		assertEquals(m, _collector.getMetric(METRIC_NAME));
		assertTrue(m.getTotalAccumulatedTime() > MILLIS);
	}
}
