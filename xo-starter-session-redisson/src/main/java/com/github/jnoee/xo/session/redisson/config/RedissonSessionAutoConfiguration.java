package com.github.jnoee.xo.session.redisson.config;

import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.session.SessionConfigurationAware;
import com.github.jnoee.xo.session.SessionProperties;
import com.github.jnoee.xo.session.redisson.RedissonSessionBeanPostProcessor;

@Configuration
@EnableRedissonHttpSession
@EnableConfigurationProperties(SessionProperties.class)
public class RedissonSessionAutoConfiguration implements SessionConfigurationAware {
  @Bean
  RedissonSessionBeanPostProcessor redissonSessionBeanPostProcessor() {
    return new RedissonSessionBeanPostProcessor();
  }
}
