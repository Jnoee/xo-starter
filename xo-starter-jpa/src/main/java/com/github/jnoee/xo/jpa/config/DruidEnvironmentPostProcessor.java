package com.github.jnoee.xo.jpa.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * Druid默认配置处理器。
 */
public class DruidEnvironmentPostProcessor implements EnvironmentPostProcessor {
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    Boolean enableDefaultConfig =
        environment.getProperty("spring.datasource.druid.enable-default-config", Boolean.class);
    if (enableDefaultConfig == null || enableDefaultConfig) {
      Properties props = new Properties();
      props.put("spring.datasource.druid.max-wait", 15000);
      props.put("spring.datasource.druid.pool-prepared-statements", false);
      props.put("spring.datasource.druid.max-open-prepared-statements", 0);
      props.put("spring.datasource.druid.validation-query", "select 'x'");
      props.put("spring.datasource.druid.test-while-idle", true);
      props.put("spring.datasource.druid.test-on-borrow", true);
      props.put("spring.datasource.druid.test-on-return", false);
      props.put("spring.datasource.druid.time-between-eviction-runs-millis", 60000);
      props.put("spring.datasource.druid.min-evictable-idle-time-millis", 300000);
      props.put("spring.datasource.druid.remove-abandoned", false);
      props.put("spring.datasource.druid.remove-abandoned-timeout", 1800);
      props.put("spring.datasource.druid.log-abandoned", true);
      props.put("spring.datasource.druid.filters", "stat,slf4j");
      props.put("spring.datasource.druid.web-stat-filter.session-stat-enable", false);
      PropertiesPropertySource propertiesPropertySource =
          new PropertiesPropertySource("druidDefaultProperties", props);
      environment.getPropertySources().addLast(propertiesPropertySource);
    }
  }
}
