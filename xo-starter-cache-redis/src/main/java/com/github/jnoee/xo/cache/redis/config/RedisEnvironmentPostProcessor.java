package com.github.jnoee.xo.cache.redis.config;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;

public class RedisEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public RedisEnvironmentPostProcessor() {
    addDefaultProperty("spring.redis.timeout", 10000);
    addDefaultProperty("spring.redis.lettuce.pool.max-active", 10);
    addDefaultProperty("spring.redis.lettuce.pool.max-idle", 5);
    addDefaultProperty("spring.redis.lettuce.pool.min-idle", 5);
    addDefaultProperty("spring.redis.lettuce.pool.max-wait", 10000);
  }
}
