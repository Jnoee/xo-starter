package com.github.jnoee.xo.cache.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.jnoee.xo.cache.CacheSerializer;

import lombok.Getter;
import lombok.Setter;

/**
 * Redis缓存配置。
 */
@Getter
@Setter
@ConfigurationProperties("xo.cache.redis")
public class RedisCacheProperties {
  /** 默认存活时间（秒） */
  private Integer ttl = 120;
  /** 是否缓存空值 */
  private Boolean cachingNullValues = false;
  /** 序列化器 */
  private CacheSerializer serializer = CacheSerializer.FST;
}
