# avaje-metric-api

The Public API for avaje metric.

This project started as a fork and refactor of https://github.com/codahale/metrics but is now significantly different.

## Design Goals

- Low overhead: Statistics collectors are kept simple (count/total/average/maximum). Makes use of jsr166e `LongAdder` and `LongMaxUpdater` which minimises contention that can occur when using AtomicLong on systems with lots of cores. The overhead of using Histograms and MovingAverages is higher.

- Metrics are collected and reported frequently (every minute typically). 

- You can add metrics you your JVM/Java Application without ANY code changes by using enhancement. Classes annotated with `@Singleton`, JAX-RS Annotations (like `@Path`, `@Comsumes`, `@Produces`) or Spring sterotypes (like `@Service`, `@Component`, `@Repository` etc) can have their public and protected methods automatically enhanced to collect timing metrics.


### Business Drivers

With good application performance metrics we can gain insight into the application. This can improve
communication between Business, Development and DevOps. Without good metrics the following questions 
can be hard to answer:

- How is the application performing in Production (relative to some baseline)?
- Seasonal/peak loads: When do they occur and how well are they handled?
- Performance Trends: How does performance compare to last week / last month / previous release / growing data ?
- Early performance issue detection: Collect metrics during development, collect it all the time.
- Capacity planning: How close to capacity is our running environment?
- How does performance compared between environments (PROD, TEST, DEV) and servers?
 

### Use via enhancement or via code

You can use avaje-metrics using code and currently enhancement only adds `TimedMetric`s and not `CounterMetric` or `ValueMetric` so you need to write code to add those. However frequently people are mostly interested in collecting timing metrics on the various parts of their application (certainly initially) and using enhancement means this can be done without any code changes (for JAX-RS, Spring applications).

Using enhancement also makes it painless to collect both success and error statistics. When collecting error statistics using code you typically need to write catch blocks and that is not FUN. When using enhancement when any timed method throws an exception that timed event goes into separate error statistics. 


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

