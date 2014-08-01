# avaje-metric-api

The Public API for avaje metric.

This project started as a fork and refactor of https://github.com/codahale/metrics but is now significantly different.

## Design Goals

- All the metrics are collected and reported frequently (every minute or every 5 minutes). 

- Statistics collectors are kept simple (count/total/average/maximum) with relatively small overhead cost of collection.


#### TimedMetric via Code

A `TimedMetric` example using code:


```java
package org.example.service;
  ...
  public class MyService {
  
    /**
     * Create a TimedMetric with name "org.example.service.MyService.sayHello" 
     */
    private static final TimedMetric METRIC_SAY_HELLO = MetricManager.getTimedMetric(MyService.class, "sayHello");

    public String sayHello(String name) {

        long startNanos = System.nanoTime();
        try {
            ...
            return "Hello "+name;
            
        } finally {
            // A TimedMetric can collect both success and error statistics
            // Here we add treating the event as a 'success' 
            METRIC_SAY_HELLO.addEventSince(true, startNanos);
        }
    }
}
```


#### TimedMetric via annotation 

Put `@Timed` on a class so that timing metrics are collected on each public method.


```java
package org.example.service;
  ...

  @Timed
  public class MyService {
    ...
  }

}
```

Use `@NotTimed` to exclude specific methods from being timed.


```java
package org.example.service;
  ...

  @Timed
  public class MyService {
  
    ...
    
    @NotTimed
    public String sayHello(String name) {
      ...
    }

}
```

Alternatively you can put `@Timed` on specific methods.


```java
package org.example.service;
  ...
  public class MyService {
  
    @Timed
    public String sayHello(String name) {
      ...
    }
}
```

## Why not use codahale/metrics?

- For "Timed Events" codehale/metrics is orientated towards using Moving Averages and Histograms and
  these are relatively heavy weight collectors.

- Provide separate error and non-error metric collection for "Timed Events". This is
useful when collecting metrics where errors might have quite a different value characteristics 
(as in the case of soap operations and database operations etc). This means the 'statistics' for
error events are keep separate from the 'normal behaviour statistics'. It also means you can 
easily monitor the error rate (error count to success count) for each metric.

- Although Moving Averages are good, once you go to collecting and reporting metrics every minute then 
moving averages (expotentially weighted moving averages) are quite laggy relative to the actual aggregate 
statistics collected and reset every minute. If you didn't collect and report the statistics every minute 
(or more frequently) then the Moving Averages would be great but the intention for avaje-metrics is to collect
and report the statistics every minute by default. 

- Histograms are good but with min and max collected and reported every minute you can get a
similar value with a much lower overhead.

- In summary avaje-metric has moved away from Moving Averages and Histograms in favour of regular collection
and reporting of simple aggregate statistics.


 

## License

Published under Apache Software License 2.0, see LICENSE

Also refer to https://github.com/codahale/metrics.

