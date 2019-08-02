package com.github.jnoee.xo.cache.config;

import org.redisson.spring.session.RedissonSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Session过期时间处理器。
 */
public class SessionBeanPostProcessor implements BeanPostProcessor {
  @Autowired
  private SessionProperties sessionProperties;

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof RedissonSessionRepository) {
      RedissonSessionRepository repo = (RedissonSessionRepository) bean;
      repo.setDefaultMaxInactiveInterval(sessionProperties.getTimeout() * 60);
      repo.setKeyPrefix(sessionProperties.getNamespace());
    }
    return bean;
  }
}
