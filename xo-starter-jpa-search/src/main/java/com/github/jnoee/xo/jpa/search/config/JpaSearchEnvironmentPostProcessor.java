package com.github.jnoee.xo.jpa.search.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

public class JpaSearchEnvironmentPostProcessor implements EnvironmentPostProcessor {
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    Properties props = new Properties();
    props.put("spring.jpa.properties.hibernate.search.default.indexmanager", "elasticsearch");
    props.put("spring.jpa.properties.hibernate.search.default.elasticsearch.required_index_status",
        "yellow");
    PropertiesPropertySource propertiesPropertySource =
        new PropertiesPropertySource("jpaSearchDefaultProperties", props);
    environment.getPropertySources().addLast(propertiesPropertySource);
  }
}
