package org.avaje.metric;

import java.io.IOException;

/**
 * Visitor for the statistics held by a metric.
 */
public interface MetricVisitor {

  /**
   * Visit a TimedMetric.
   */
  public void visit(TimedMetric metric) throws IOException;

  /**
   * Visit a BucketTimedMetric.
   */
  public void visit(BucketTimedMetric metric) throws IOException;
  
  /**
   * Visit a ValueMetric.
   */
  public void visit(ValueMetric metric) throws IOException;
  
  /**
   * Visit a CounterMetric.
   */
  public void visit(CounterMetric metric) throws IOException;

  /**
   * Visit an individual GaugeMetric.
   */
  public void visit(GaugeDoubleMetric gaugeMetric) throws IOException;

  /**
   * Visit a GaugeMetricGroup.
   */
  public void visit(GaugeDoubleGroup gaugeMetricGroup) throws IOException;

  /**
   * Visit an individual IntGaugeMetric.
   */
  public void visit(GaugeLongMetric gaugeMetric) throws IOException;
  
  /**
   * Visit a IntGaugeMetricGroup.
   */
  public void visit(GaugeLongGroup gaugeMetricGroup) throws IOException;
}
