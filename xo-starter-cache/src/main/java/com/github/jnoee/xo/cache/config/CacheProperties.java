package com.github.jnoee.xo.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.github.jnoee.xo.cache.CacheSerializer;

import lombok.Data;

/**
 * 缓存配置。
 */
@Data
@ConfigurationProperties("xo.cache")
public class CacheProperties {
  /** 序列化方式 */
  private CacheSerializer serializer = CacheSerializer.FST;
  /** redisson配置文件 */
  private String configFile = "classpath:redisson.yml";
}
