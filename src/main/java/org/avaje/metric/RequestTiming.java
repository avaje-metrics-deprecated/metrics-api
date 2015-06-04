package org.avaje.metric;

import java.util.List;

/**
 * Holds the details for a request including it's timing entries.
 */
public interface RequestTiming {

  /**
   * Return the time the request was reported.
   */
  long getReportTime();

  /**
   * Return the entries for the request.
   */
  List<RequestTimingEntry> getEntries();

}
