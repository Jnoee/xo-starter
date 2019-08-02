package com.github.jnoee.xo.jpa.cache;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.CacheManager;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

public class JpaCacheRegionFactoryPostProcessor implements BeanPostProcessor {
  @Autowired
  private CacheManager cacheManager;
  private Set<String> beanNames = new HashSet<>();

  @Override
  public Object postProcessBeforeInitialization(final Object bean, final String beanName) {
    if (bean instanceof AbstractEntityManagerFactoryBean) {
      JpaCacheRegionFactory.SPRING_CACHE_MANAGER.set(cacheManager);
      beanNames.add(beanName);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, final String beanName) {
    if (beanNames.remove(beanName) && beanNames.isEmpty()) {
      JpaCacheRegionFactory.SPRING_CACHE_MANAGER.remove();
    }
    return bean;
  }
}
