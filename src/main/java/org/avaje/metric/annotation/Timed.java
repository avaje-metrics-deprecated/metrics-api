package org.avaje.metric.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.avaje.metric.BucketTimedMetric;
import org.avaje.metric.TimedMetric;

/**
 * Marker that public methods should have timed execution statistics collected.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Timed {

  /**
   * Set the method name part of the full metric name.
   * <p>
   * This is used when the method name is not appropriate or when there is method overloading and
   * the otherwise generated unique name is unclear.
   * <p>
   * The package and class names are still used and prepended to this name value.
   */
  String name() default "";

  /**
   * Set the full name of the metric.
   * <p>
   * Provides a complete replacement of the metric name. The package and class names are not used at
   * all.
   */
  String fullName() default "";

  
  /**
   * Define buckets as a list of millisecond times.
   * <p>
   * For example with values of 100, 200, 300 there a with 4 bucket ranges of:
   * <pre>
   *      0 to 100 milliseconds
   *    100 to 200 milliseconds
   *    200 to 300 milliseconds
   *    300+       milliseconds
   * </pre>
   * <p>
   * Defining buckets means a {@link BucketTimedMetric} will be used instead of a {@link TimedMetric}.
   */
  int[] buckets() default {};
}
