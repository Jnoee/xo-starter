package com.github.jnoee.xo.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Session配置。
 */
@Getter
@Setter
@ConfigurationProperties("xo.session")
public class SessionProperties {
  /** 是否在HTTP Header中设置Token */
  private Boolean token = true;
  /** HTTP Header中设置的Token名称 */
  private String tokenName = "x-auth-token";
  /** 命名空间 */
  private String namespace = "su:session";
  /** 过期时间（分钟） */
  private Integer timeout = 120;
}
