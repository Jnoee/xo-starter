package com.github.jnoee.xo.session.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.github.jnoee.xo.cache.redis.serializer.SerializerFactory;
import com.github.jnoee.xo.session.SessionConfigurationAware;
import com.github.jnoee.xo.session.SessionProperties;
import com.github.jnoee.xo.session.redis.RedisSessionBeanPostProcessor;

@Configuration
@EnableRedisHttpSession
@EnableConfigurationProperties(SessionProperties.class)
public class RedisSessionAutoConfiguration implements SessionConfigurationAware {
  @Autowired
  private SessionProperties sessionProperties;

  @Bean
  public ConfigureRedisAction configureRedisAction() {
    return ConfigureRedisAction.NO_OP;
  }

  @Bean
  RedisSessionBeanPostProcessor redisSessionBeanPostProcessor() {
    return new RedisSessionBeanPostProcessor();
  }

  @Bean
  RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    return SerializerFactory.create(sessionProperties.getSerializer());
  }
}
