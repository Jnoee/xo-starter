package com.github.jnoee.xo.cache.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * 缓存配置。
 */
@Data
@ConfigurationProperties("xo.cache.redisson")
public class RedissonCacheProperties {
  /** 是否打开x-auth-token */
  private Boolean xAuthToken = true;
  /** redisson配置文件 */
  private String configFile = "classpath:redisson.yml";
}
