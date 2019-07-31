package com.github.jnoee.xo.dfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 文件存储通用配置。
 */
@Data
@ConfigurationProperties("xo.dfs")
public class DfsProperties {
  private String type = "local";
  private String site = "http://localhost:8080/";
}
