package com.github.jnoee.xo.cache.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.jnoee.xo.cache.CacheSerializer;

import lombok.Data;

/**
 * 缓存配置。
 */
@Data
@ConfigurationProperties("xo.cache.redisson")
public class RedissonCacheProperties {
  /** 序列化方式 */
  private CacheSerializer serializer = CacheSerializer.FST;
  /** redisson配置文件 */
  private String configFile = "classpath:redisson.yml";
}
