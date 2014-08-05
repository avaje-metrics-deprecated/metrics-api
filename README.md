# avaje-metrics

## Overview

This project started as a fork of https://github.com/codahale/metrics but became significantly different. Frequent reporting of metrics meant that Avaje Metrics has generally moved away from Moving Averages and Histograms/percentiles to relatively simpler and cheaper counters (Count, Total, Average, Max). When frequent collection of Max and Average latency is not sufficent you can look to use `BucketTimedMetric` to get a clearer picture of how latency is distributed (because generally it is not 'normally distributed').

- Statistics can lie
- Latency is very frequently not normally distributed
- If you use Standard Deviation you probably should be careful
- If you don't collect frequently then Average and Max can easily become relatively meaningless (If you collect every hour then trying to interpret the meaning of a Average or Max value might well be pointless).


`LongAdder` and `LongMaxUpdater` are important implementation details that make statistical counters viable in production with low overhead and avoiding contention. There are alternatives but for me these are the shining light and provided to use by some very clever folks - thanks!!



## Design Goals

- Collect metrics in Production with low overhead
- Report metrics frequently to enable reasonable interpretation (Statistics can lie)
- Keep it simple to use
- Option to use an agent/enhancement to automatically instrument an existing JAX-RS or Spring application 
 

### Collect metrics in Production

To collect metrics in production it is important to keep the overhead low and for the statistical counters to not introduce contention. Thankfully Doug Lea and friends have built some counters to do exactly what we need as part of JSR166e/JDK8. Avaje Metrics makes use of `LongAdder` and `LongMaxUpdater` (backported from JDK8). Some might say that Avaje Metrics is an glorified wrapper for LongAdder and LongMaxUpdater.


### Report metrics frequently 

When metrics are reported relatively frequently then the ability to reason and interpret simple count, total, mean and max values increases. Conversely, if instead a mean and max value related to a long period of time like 1 hour then frequently they become relatively meaningless to interpret. This is due to latency generally not being statistically 'normally' distributed. The argument for using percentiles and histograms is strong if you don't collect metrics frequently enough.

Note that `BucketTimedMetric` provides a nice alternative to Histograms/Percentiles and I expect users will frequently look to using `BucketTimedMetric` on things like web/rest endpoints. 


### Keep it simple 

- Easy to add metrics to your application via enhancement or code
- Keep the internals simple and rely on `LongAdder` and `LongMaxUpdater`
- Easy collection and reporting


### Automatically instrument a JAX-RS or Spring application

Use the metrics agent to instrucment classes annotated with `@Singleton`, JAX-RS Annotations (like `@Path`, `@Comsumes`, `@Produces`) or Spring sterotypes (like `@Service`, `@Component`, `@Repository` etc). This provides an easy way to try it out on your existing application with virtually no effort.


## Business Drivers

For developers collecting metrics on your application can be good fun and very interesting. It is worth remembering that there are important questions and business drivers to motivate the collection and reporting of these metrics. 

- How is the application currently performing in Production (relative to some baseline)
- When do Seasonal/peak loads occur and effect do they have
- Can performance trends be detected due to software releases, configuration changes, growing data
- During development can likely performance issues be detected
- What is the maximum load the system can handle
- Are there specific SLA requirements that need to be monitored




# Getting Started

## 1. Maven dependencies

Add the following 2 dependencies to your project.

```xml
<dependency>
    <groupId>org.avaje.metric</groupId>
    <artifactId>avaje-metric-api</artifactId>
    <version>3.5.0</version>
</dependency>

<dependency>
    <groupId>org.avaje.metric</groupId>
    <artifactId>avaje-metric-core</artifactId>
    <version>3.5.0</version>
</dependency>
```


## 2. Enhancement

If you application already uses `@Singleton`, or Spring `@Service`, `@Component`, etc or JAX-RS `@Path` then you can go ahead and use the `enhance-maven-plugin` to search for and instrument those classes for you.

If not then you can add `@Timed` to classes or methods that you want to be instrumented.

Using enhancement is optional. You can add metrics collection via code (although it is more work especially noting that the enhancment detects when exceptions are thrown and puts the execution times into the separate 'error statistics').

#### Maven build plugin

To your maven pom add the plugin like below and specify the packages you wish it to scan for classes it should enhance. Take note of the packages element in the maven plugin xml below.

```xml
  <build>
    
    <plugin>
      <groupId>org.avaje.metric</groupId>
      <artifactId>enhance-maven-plugin</artifactId>
      <version>3.5.0</version>
      <executions>
         <execution>
          <id>main</id>
          <phase>process-classes</phase>
          <configuration>
            <classSource>target/classes</classSource>
            <packages>org.example.myapp.**</packages>
            <transformArgs>debug=1</transformArgs>
          </configuration>
          <goals>
            <goal>enhance</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
    
  </build>
```

## 3. MetricReporter

Add a MetricReporter instance to your application.

```java
...
import org.avaje.metric.report.FileReporter;
import org.avaje.metric.report.MetricReportManager;
...

  // Just use the simple FileReporter that comes with avaje-metric-core.
  // Writes metrics in a csv format every 60 seconds. Defaults to write a
  // daily file and keep a maximum of 20 files
  
  FileReporter file = new FileReporter();
  MetricReportManager reportManager = new MetricReportManager(60, file);


```

## 4. metric-name-mapping.txt (Optional)

You can optionally add a metric-name-mapping.txt file to your src/main/resources.  
In this file you can put some key=value pairs that can be used to modify the metrics names -
typically trimming the package name part and adding some prefixs like `web.api`, `web.sockets`, `data`, `integration`. 

The primary reason for doing this is so that it is easier to rollup/group related metrics and
much of the package name is redundant. 

```properties
org.example.myapp.endpoint=web.api
org.example.myapp.repository.dao=dataaccess
org.example.myapp=myapp
```

## 5. Add Metrics via Code

You can add metric via code.

#### TimedMetric

```java
package org.example.service;
  ...
  public class MyService {
  
    /**
     * Create a TimedMetric with name "org.example.service.MyService.sayHello".
     * Typically the metrics are static fields to avoid some map lookup overhead.
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

# Appendix

## @Timed and @NotTimed annotations

You can additionally add a @Timed annotation on classes or methods to indicate that
this class (all public methods) or a specific method should be enhanced to collect timing metrics.

You can use @NotTimed to specify that a class or method should not have any timing metrics.


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


## Why not use Netflix/servo?
 
This is a library worth looking at. The BasicTimer has a similar design but personally I do not like the internals (use of synchronized, extra GC and not using LongAdder or LongMaxUpdater). It does have some interesting features and is worth watching.


## License

Published under Apache Software License 2.0, see LICENSE


## Similar Projects

- https://github.com/codahale/metrics
- https://github.com/Netflix/servo
- https://code.google.com/p/javasimon/
- https://github.com/perf4j/perf4j
 
## Related Links

- http://gee.cs.oswego.edu/dl/jsr166/dist/jsr166edocs/jsr166e/package-summary.html
- http://minddotout.wordpress.com/2013/05/11/java-8-concurrency-longadder/

