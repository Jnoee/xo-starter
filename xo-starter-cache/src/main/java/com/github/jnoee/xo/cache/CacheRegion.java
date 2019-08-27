package com.github.jnoee.xo.cache;

import lombok.Getter;
import lombok.Setter;

/**
 * Cache配置。
 */
@Getter
@Setter
public class CacheRegion {
  /** 缓存名称 */
  private String name;
  /** 存活时间（秒） */
  private Integer ttl = 120;
  /** 最大空闲时间（秒） */
  private Integer maxIdleTime = 60;
  /** 最大数量 */
  private Integer maxSize = 1000;
  /** 是否缓存空值 */
  private Boolean cachingNullValues = false;

  public CacheRegion(String name) {
    this.name = name;
  }

  public CacheRegion(String name, Integer ttl) {
    this(name);
    this.ttl = ttl;
  }

  public CacheRegion(String name, Integer ttl, Integer maxIdelTime) {
    this(name, ttl);
    this.maxIdleTime = maxIdelTime;
  }

  public CacheRegion(String name, Integer ttl, Integer maxIdelTime, Integer maxSize) {
    this(name, ttl, maxIdelTime);
    this.maxSize = maxSize;
  }

  public CacheRegion(String name, Integer ttl, Boolean cachingNullValues) {
    this(name, ttl);
    this.cachingNullValues = cachingNullValues;
  }
}
