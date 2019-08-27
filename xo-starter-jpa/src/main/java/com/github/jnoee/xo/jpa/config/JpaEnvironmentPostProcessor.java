package com.github.jnoee.xo.jpa.config;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;
import com.github.jnoee.xo.jpa.cache.JpaCacheRegionFactory;

/**
 * JPA默认配置处理器。
 */
public class JpaEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public JpaEnvironmentPostProcessor() {
    addDefaultProperty("spring.jpa.hibernate.ddl-auto", "none");
    addDefaultProperty("spring.jpa.hibernate.naming.physical-strategy",
        PhysicalNamingStrategyStandardImpl.class.getName());
    addDefaultProperty("spring.jpa.properties.hibernate.cache.use_query_cache", true);
    addDefaultProperty("spring.jpa.properties.hibernate.cache.region.factory_class",
        JpaCacheRegionFactory.class.getName());
  }
}
