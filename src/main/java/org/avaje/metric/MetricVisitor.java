package org.avaje.metric;

/**
 * Visitor for the statistics held by a metric.
 */
public interface MetricVisitor {

  /**
   * Visit a TimedMetric.
   */
  public void visit(TimedMetric metric);

  /**
   * Visit a BucketTimedMetric.
   */
  public void visit(BucketTimedMetric metric);
  
  /**
   * Visit a ValueMetric.
   */
  public void visit(ValueMetric metric);
  
  /**
   * Visit a CounterMetric.
   */
  public void visit(CounterMetric metric);

  /**
   * Visit an individual GaugeMetric.
   */
  public void visit(GaugeDoubleMetric gaugeMetric);

  /**
   * Visit a GaugeMetricGroup.
   */
  public void visit(GaugeDoubleGroup gaugeMetricGroup);

  /**
   * Visit an individual IntGaugeMetric.
   */
  public void visit(GaugeLongMetric gaugeMetric);
  
  /**
   * Visit a IntGaugeMetricGroup.
   */
  public void visit(GaugeLongGroup gaugeMetricGroup);
}
