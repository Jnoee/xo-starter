package com.github.jnoee.xo.jpa.config;

import java.util.Properties;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

import com.github.jnoee.xo.config.GenericEnvironmentPostProcessor;
import com.github.jnoee.xo.jpa.cache.JpaCacheRegionFactory;

/**
 * JPA默认配置处理器。
 */
public class JpaEnvironmentPostProcessor implements GenericEnvironmentPostProcessor {
  @Override
  public String getPropertiesName() {
    return "jpaDefaultProperties";
  }

  @Override
  public String getEnablePropertyName() {
    return "spring.jpa.hibernate.enable-default-config";
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();
    props.put("spring.jpa.hibernate.ddl-auto", "none");
    props.put("spring.jpa.hibernate.naming.physical-strategy",
        PhysicalNamingStrategyStandardImpl.class.getName());
    props.put("spring.jpa.properties.hibernate.cache.use_query_cache", true);
    props.put("spring.jpa.properties.hibernate.cache.region.factory_class",
        JpaCacheRegionFactory.class.getName());
    return props;
  }
}
