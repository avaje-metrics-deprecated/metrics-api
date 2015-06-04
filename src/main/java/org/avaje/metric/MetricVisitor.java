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
  void visit(TimedMetric timed) throws IOException;

  /**
   * Visit a BucketTimedMetric.
   */
  void visit(BucketTimedMetric bucket) throws IOException;

  /**
   * Visit a ValueMetric.
   */
  void visit(ValueMetric value) throws IOException;

  /**
   * Visit a CounterMetric.
   */
  void visit(CounterMetric counter) throws IOException;

  /**
   * Visit an individual GaugeDoubleMetric.
   */
  void visit(GaugeDoubleMetric gauge) throws IOException;

  /**
   * Visit a GaugeDoubleGroup.
   */
  void visit(GaugeDoubleGroup gaugeGroup) throws IOException;

  /**
   * Visit an individual GaugeLongMetric.
   */
  void visit(GaugeLongMetric gauge) throws IOException;

  /**
   * Visit a GaugeLongGroup.
   */
  void visit(GaugeLongGroup gaugeGroup) throws IOException;
}
