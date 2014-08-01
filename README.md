# avaje-metric-api

The Public API for avaje metric.

This project started as a fork and refactor of https://github.com/codahale/metrics but is now significantly different.

## Design Goals

- Low overhead: Statistics collectors are kept simple (count/total/average/maximum). Use `LongAdder` and `LongMaxUpdater` which minimises contention that can occur when using AtomicLong (e.g. http://minddotout.wordpress.com/2013/05/11/java-8-concurrency-longadder/). The overhead of using Histograms and MovingAverages is higher.

- Metrics are collected and reported frequently (every minute typically). 

- You can add metrics you your JVM/Java Application without any code changes by using enhancement. Classes annotated with `@Singleton`, JAX-RS Annotations (like `@Path`, `@Comsumes`, `@Produces`) or Spring sterotypes (like `@Service`, `@Component`, `@Repository` etc) can have their public and protected methods automatically enhanced to collect timing metrics.


## Business Drivers

With good application performance metrics we can gain insight into the application. This can improve
communication between Business, Development and DevOps. Without good metrics the following questions 
can be hard to answer:

- How is the application performing in Production (relative to some baseline)?
- Seasonal/peak loads: When do they occur and how well are they handled?
- Performance Trends: How does performance compare to last week / last month / previous release / growing data ?
- Early performance issue detection: Collect metrics during development, collect it all the time.
- Capacity planning: How close to capacity is our running environment?
- How does performance compared between environments (PROD, TEST, DEV) and servers?
 

## Enhancement or Code

You can use avaje-metrics using code and currently enhancement only adds `TimedMetric`s and not `CounterMetric` or `ValueMetric` so you need to write code to add those. However frequently people are mostly interested in collecting timing metrics on the various parts of their application (certainly initially) and using enhancement means this can be done without any code changes (for JAX-RS, Spring applications).

Using enhancement also makes it painless to collect both success and error statistics. When collecting error statistics using code you typically need to write catch blocks and that is not FUN. When using enhancement when any timed method throws an exception that timed event goes into separate error statistics. 

## Maven dependencies

Add the following 2 dependencies to your project.

```xml
<dependency>
    <groupId>org.avaje.metric</groupId>
    <artifactId>avaje-metric-api</artifactId>
    <version>3.2.0</version>
</dependency>

<dependency>
    <groupId>org.avaje.metric</groupId>
    <artifactId>avaje-metric-core</artifactId>
    <version>3.2.0</version>
</dependency>
```


## Getting started with enhancement

### Maven build plugin

With maven add a build plugin `org.avaje.metric`, `enhance-maven-plugin` and specify the packages that you 
want to be scanned for classes annotated with `@Timed`, `@Singleton`,  JAX-RS or Spring annotations.

```xml
  <build>
    
    <plugin>
      <groupId>org.avaje.metric</groupId>
      <artifactId>enhance-maven-plugin</artifactId>
      <version>3.2.0</version>
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

### MetricReporter

Add a MetricReporter instance to your application.

```java
...
import org.avaje.metric.filereport.FileReporter;
import org.avaje.metric.report.MetricReportManager;
...

  // Just use the simple FileReporter that comes with avaje-metric-core.
  // Writes metrics in a csv format every 60 seconds. Defaults to write a
  // daily file and keep a maximum of 20 files
  
  FileReporter file = new FileReporter();
  MetricReportManager reportManager = new MetricReportManager(60, file);


```

### metric-name-mapping.txt

You can optionally add a metric-name-mapping.txt file to your src/main/resources.  
In this file you can put some key=value pairs that can be used to modify the metrics names -
typically trimming the package name part and adding some prefixs like `web.api`, `web.sockets`, `dataaccess`, `integration`. 

The primary reason for doing this is so that it is easier to rollup/group related metrics and
much of the package name is redundant. 

```properties
org.example.myapp.endpoint=web.api
org.example.myapp.repository.dao=dataaccess
org.example.myapp=myapp
```

### @Timed and @NotTimed annotations

You can additionally add a @Timed annotation on classes or methods to indicate that
this class (all public methods) or a specific method should be enhanced to collect timing metrics.

You can use @NotTimed to specify that a class or method should not have any timing metrics.



## Using Code

You can add TimedMetrics using code and currently for ValueMetric and GaugeMetric you need to
write code to add these metrics.


### TimedMetric


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
 

