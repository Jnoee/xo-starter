package com.github.jnoee.xo.jpa.config;

import com.github.jnoee.xo.cache.AbstractCacheSettings;

/**
 * 查询缓存配置。
 */
public class CacheSettings extends AbstractCacheSettings {
  public CacheSettings() {
    addRegion("default-update-timestamps-region", "default-query-results-region");
  }
}
