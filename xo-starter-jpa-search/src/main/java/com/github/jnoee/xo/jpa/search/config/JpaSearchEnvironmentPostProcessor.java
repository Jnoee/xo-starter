package com.github.jnoee.xo.jpa.search.config;

import com.github.jnoee.xo.config.AbstractEnvironmentPostProcessor;

public class JpaSearchEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor {
  public JpaSearchEnvironmentPostProcessor() {
    addDefaultProperty("spring.jpa.properties.hibernate.search.default.indexmanager",
        "elasticsearch");
    addDefaultProperty(
        "spring.jpa.properties.hibernate.search.default.elasticsearch.required_index_status",
        "yellow");
  }
}
