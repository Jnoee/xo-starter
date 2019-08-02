package com.github.jnoee.xo.cache.config;

import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableRedissonHttpSession
@EnableConfigurationProperties(SessionProperties.class)
public class SessionAutoConfiguration {
  @Bean
  @ConditionalOnProperty(name = "xo.session.token", havingValue = "true", matchIfMissing = true)
  HttpSessionIdResolver httpSessionIdResolver(SessionProperties sessionProperties) {
    return new HeaderHttpSessionIdResolver(sessionProperties.getTokenName());
  }

  @Bean
  SessionBeanPostProcessor redissonSessionBeanPostProcessor() {
    return new SessionBeanPostProcessor();
  }
}
