package com.github.jnoee.xo.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * API文档配置属性。
 */
@Data
@ConfigurationProperties(prefix = "xo.api.doc")
public class SwaggerProperties {
  private String title;
  private String description;
  private String version;
  private String basePackage;
}
