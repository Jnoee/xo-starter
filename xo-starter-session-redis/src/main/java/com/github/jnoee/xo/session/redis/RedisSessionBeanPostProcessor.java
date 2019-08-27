package com.github.jnoee.xo.session.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import com.github.jnoee.xo.session.SessionProperties;

/**
 * Session过期时间处理器。
 */
public class RedisSessionBeanPostProcessor implements BeanPostProcessor {
  @Autowired
  private SessionProperties sessionProperties;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof RedisOperationsSessionRepository) {
      RedisOperationsSessionRepository repo = (RedisOperationsSessionRepository) bean;
      repo.setDefaultMaxInactiveInterval(sessionProperties.getTimeout() * 60);
      repo.setRedisKeyNamespace(sessionProperties.getNamespace());
    }
    return bean;
  }
}
