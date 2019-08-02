package com.github.jnoee.xo.jpa.cache;

import java.net.URI;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;

public class JpaCacheManager implements CacheManager {
  private org.springframework.cache.CacheManager springCacheManager;
  private ConcurrentMap<String, Cache<?, ?>> nameToCache = new ConcurrentHashMap<>();
  private AtomicBoolean closed = new AtomicBoolean(false);
  private Boolean unwrapJcache;

  public JpaCacheManager(org.springframework.cache.CacheManager springCacheManager,
      Boolean unwrapJcache) {
    this.springCacheManager = springCacheManager;
    this.unwrapJcache = unwrapJcache;
    for (String cacheName : springCacheManager.getCacheNames()) {
      nameToCache.put(cacheName, new JpaCache<>(this, springCacheManager.getCache(cacheName)));
    }
  }

  @Override
  public CachingProvider getCachingProvider() {
    throw new UnsupportedOperationException();
  }

  @Override
  public URI getURI() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClassLoader getClassLoader() {
    return springCacheManager.getClass().getClassLoader();
  }

  @Override
  public Properties getProperties() {
    throw new UnsupportedOperationException();
  }

  @Override
  public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(final String cacheName,
      final C configuration) {
    throw new UnsupportedOperationException(
        "Cache creation is not supported. cacheName: " + cacheName);
  }

  @Override
  public <K, V> Cache<K, V> getCache(final String cacheName, final Class<K> keyType,
      final Class<V> valueType) {
    return getCache(cacheName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <K, V> Cache<K, V> getCache(final String cacheName) {
    checkClosed();
    Cache<K, V> cache = (Cache<K, V>) nameToCache.get(cacheName);
    if (cache == null) {
      final org.springframework.cache.Cache springCache = springCacheManager.getCache(cacheName);
      if (springCache != null) {
        final Cache<K, V> jCache;
        if (unwrapJcache && springCache.getNativeCache() instanceof Cache) {
          jCache = (Cache<K, V>) springCache.getNativeCache();
        } else {
          jCache = new JpaCache<>(this, springCache);
        }
        cache = (Cache<K, V>) nameToCache.putIfAbsent(cacheName, jCache);
      }
    }
    return cache;
  }

  @Override
  public Iterable<String> getCacheNames() {
    checkClosed();
    return Collections.unmodifiableCollection(nameToCache.keySet());
  }

  @Override
  public void destroyCache(final String cacheName) {
    checkClosed();
    final Cache<?, ?> cache = nameToCache.remove(cacheName);
    if (cache != null) {
      cache.close();
    }
  }

  @Override
  public void enableManagement(final String cacheName, final boolean enabled) {
    checkClosed();
  }

  @Override
  public void enableStatistics(final String cacheName, final boolean enabled) {
    checkClosed();
  }

  @Override
  public void close() {
    for (final Cache<?, ?> cache : nameToCache.values()) {
      cache.close();
    }
    closed.set(false);
  }

  @Override
  public boolean isClosed() {
    return closed.get();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(final Class<T> clazz) {
    if (clazz.isInstance(springCacheManager)) {
      return (T) springCacheManager;
    }
    throw new IllegalArgumentException();
  }

  private void checkClosed() {
    if (closed.get()) {
      throw new IllegalStateException("Cache is closed");
    }
  }

}
