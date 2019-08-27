package com.github.jnoee.xo.web.config;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;

/**
 * Jackson默认配置处理器。
 */
public class JacksonEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public JacksonEnvironmentPostProcessor() {
    addDefaultProperty("spring.jackson.default-property-inclusion", "non_null");
  }
}
