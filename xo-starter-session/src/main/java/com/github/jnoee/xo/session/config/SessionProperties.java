package com.github.jnoee.xo.session.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.jnoee.xo.cache.CacheSerializer;

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
  /** 序列化器 */
  private CacheSerializer serializer = CacheSerializer.FST;
  /** 命名空间 */
  private String namespace = "xo:session";
  /** 过期时间（分钟） */
  private Integer timeout = 120;
}
