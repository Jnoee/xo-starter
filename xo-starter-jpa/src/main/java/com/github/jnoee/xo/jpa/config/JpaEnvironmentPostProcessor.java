package com.github.jnoee.xo.jpa.config;

import java.util.Properties;

import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.integralblue.hibernate.cache.springcache.SpringCacheRegionFactory;

/**
 * JPA默认配置处理器。
 */
public class JpaEnvironmentPostProcessor implements EnvironmentPostProcessor {
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    Boolean enableDefaultConfig =
        environment.getProperty("spring.jpa.hibernate.enable-default-config", Boolean.class);
    if (enableDefaultConfig == null || enableDefaultConfig) {
      Properties props = new Properties();
      props.put("spring.jpa.hibernate.ddl-auto", "none");
      props.put("spring.jpa.hibernate.naming.physical-strategy",
          PhysicalNamingStrategyStandardImpl.class.getName());
      props.put("spring.jpa.properties.hibernate.cache.use_query_cache", true);
      props.put("spring.jpa.properties.hibernate.cache.region.factory_class",
          SpringCacheRegionFactory.class.getName());
      PropertiesPropertySource propertiesPropertySource =
          new PropertiesPropertySource("jpaDefaultProperties", props);
      environment.getPropertySources().addLast(propertiesPropertySource);
    }
  }
}
