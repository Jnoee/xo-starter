package com.github.jnoee.xo.banner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "xo.banner")
public class BannerProperties {
  /** 名称 */
  private String name;
  /** 说明 */
  private String description;
}
