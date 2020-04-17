package com.github.jnoee.xo.web.config;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;
import com.github.jnoee.xo.utils.DateUtils;

/**
 * Jackson默认配置处理器。
 */
public class JacksonEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public JacksonEnvironmentPostProcessor() {
    addDefaultProperty("spring.jackson.default-property-inclusion", "non_null");
    addDefaultProperty("spring.jackson.date-format", DateUtils.SECOND);
    addDefaultProperty("spring.jackson.time-zone", "GMT+8");
  }
}
