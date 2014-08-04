package org.avaje.metric;

import java.io.IOException;

/**
 * Visitor for the statistics held by a metric.
 * <p>
 * This is typically used by metric reporters that want to traverse the collected metrics and report
 * the values to a repository or write then to a file.
 */
public interface MetricVisitor {

  /**
   * Visit a TimedMetric.
   */
  public void visit(TimedMetric timed) throws IOException;

  /**
   * Visit a BucketTimedMetric.
   */
  public void visit(BucketTimedMetric bucket) throws IOException;

  /**
   * Visit a ValueMetric.
   */
  public void visit(ValueMetric value) throws IOException;

  /**
   * Visit a CounterMetric.
   */
  public void visit(CounterMetric counter) throws IOException;

  /**
   * Visit an individual GaugeDoubleMetric.
   */
  public void visit(GaugeDoubleMetric gauge) throws IOException;

  /**
   * Visit a GaugeDoubleGroup.
   */
  public void visit(GaugeDoubleGroup gaugeGroup) throws IOException;

  /**
   * Visit an individual GaugeLongMetric.
   */
  public void visit(GaugeLongMetric gauge) throws IOException;

  /**
   * Visit a GaugeLongGroup.
   */
  public void visit(GaugeLongGroup gaugeGroup) throws IOException;
}
