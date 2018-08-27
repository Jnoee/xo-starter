package com.github.jnoee.xo.cache.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Redis缓存配置。
 */
@Data
@ConfigurationProperties("su.cache.redis")
public class RedisCacheProperties {
  /** 是否打开x-auth-token */
  private Boolean xAuthToken = true;
  /** 默认存活时间（秒） */
  private Integer ttl = 120;
  /** 是否缓存空值 */
  private Boolean cachingNullValues = false;
}
