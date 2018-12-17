package com.github.jnoee.xo.jpa.config;

import com.github.jnoee.xo.cache.config.AbstractCacheSettings;

/**
 * 查询缓存配置。
 */
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    addRegion("org.hibernate.cache.spi.UpdateTimestampsCache",
        "org.hibernate.cache.internal.StandardQueryCache");
  }
}
