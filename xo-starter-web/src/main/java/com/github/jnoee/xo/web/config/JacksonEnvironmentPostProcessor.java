package com.github.jnoee.xo.web.config;

import java.util.Properties;

import com.github.jnoee.xo.config.GenericEnvironmentPostProcessor;

/**
 * Jackson默认配置处理器。
 */
public class JacksonEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "jacksonDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.jackson.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.jackson.default-property-inclusion", "non_null");
    return props;
  }
}
