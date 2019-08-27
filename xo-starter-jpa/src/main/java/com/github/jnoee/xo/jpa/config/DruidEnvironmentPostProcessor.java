package com.github.jnoee.xo.jpa.config;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;

/**
 * Druid默认配置处理器。
 */
public class DruidEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public DruidEnvironmentPostProcessor() {
    addDefaultProperty("spring.datasource.druid.initial-size", 5);
    addDefaultProperty("spring.datasource.druid.min-idle", 5);
    addDefaultProperty("spring.datasource.druid.max-active", 20);
    addDefaultProperty("spring.datasource.druid.max-wait", 15000);
    addDefaultProperty("spring.datasource.druid.pool-prepared-statements", false);
    addDefaultProperty("spring.datasource.druid.max-open-prepared-statements", 0);
    addDefaultProperty("spring.datasource.druid.validation-query", "select 'x'");
    addDefaultProperty("spring.datasource.druid.test-while-idle", true);
    addDefaultProperty("spring.datasource.druid.test-on-borrow", true);
    addDefaultProperty("spring.datasource.druid.test-on-return", false);
    addDefaultProperty("spring.datasource.druid.time-between-eviction-runs-millis", 60000);
    addDefaultProperty("spring.datasource.druid.min-evictable-idle-time-millis", 300000);
    addDefaultProperty("spring.datasource.druid.remove-abandoned", false);
    addDefaultProperty("spring.datasource.druid.remove-abandoned-timeout", 1800);
    addDefaultProperty("spring.datasource.druid.log-abandoned", true);
    addDefaultProperty("spring.datasource.druid.filters", "stat");
    addDefaultProperty("spring.datasource.druid.stat-view-servlet.enabled", true);
  }
}
