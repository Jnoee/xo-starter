package com.github.jnoee.xo.cache.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.cache.redis.lock.RedisLockAspect;
import com.github.jnoee.xo.cache.redis.lock.RedisLocker;

@Configuration
public class RedisLockAutoConfiguration {
  @Bean
  RedisLocker redisLocker() {
    return new RedisLocker();
  }

  @Bean
  RedisLockAspect redisLockAspect() {
    return new RedisLockAspect();
  }
}
