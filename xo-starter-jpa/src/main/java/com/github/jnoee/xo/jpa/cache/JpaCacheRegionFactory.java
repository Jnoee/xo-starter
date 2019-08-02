package com.github.jnoee.xo.jpa.cache;

import java.util.Map;

import javax.cache.CacheManager;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.jcache.internal.JCacheRegionFactory;

import com.github.jnoee.xo.exception.SysException;

public class JpaCacheRegionFactory extends JCacheRegionFactory {
  private static final long serialVersionUID = 8505808974850348087L;
  private static final String PROP_PREFIX = "hibernate.spring.cache";
  private static final String UNWRAP_JCACHE = PROP_PREFIX + ".unwrap_jcache";
  public static final ThreadLocal<org.springframework.cache.CacheManager> SPRING_CACHE_MANAGER =
      new InheritableThreadLocal<>();

  @Override
  protected CacheManager resolveCacheManager(SessionFactoryOptions settings,
      @SuppressWarnings("rawtypes") Map properties) {
    org.springframework.cache.CacheManager springCacheManager = SPRING_CACHE_MANAGER.get();
    if (springCacheManager == null) {
      throw new SysException("没有找到Spring的缓存管理器。");
    }
    boolean unwrapJcache = Boolean.parseBoolean((String) properties.get(UNWRAP_JCACHE));
    return new JpaCacheManager(springCacheManager, unwrapJcache);
  }
}
