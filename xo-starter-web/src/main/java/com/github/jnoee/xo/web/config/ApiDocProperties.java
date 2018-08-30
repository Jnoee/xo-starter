package com.github.jnoee.xo.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * API文档配置属性。
 */
@Data
@ConfigurationProperties(prefix = "xo.api.doc")
public class ApiDocProperties {
  private String title;
  private String description;
  private String version;
  private String basePackage;
}
