package com.github.jnoee.xo.jpa.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import javax.cache.processor.MutableEntry;

import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.core.GenericTypeResolver;

import com.github.jnoee.xo.exception.SysException;

public class JpaCache<K, V> implements Cache<K, V> {
  private org.springframework.cache.Cache springCache;
  private CacheManager cacheManager;
  private Class<V> valueClass;
  private AtomicBoolean closed = new AtomicBoolean(false);

  @SuppressWarnings("unchecked")
  public JpaCache(CacheManager cacheManager, org.springframework.cache.Cache springCache) {
    Class<?>[] genericArguments =
        GenericTypeResolver.resolveTypeArguments(getClass(), JpaCache.class);
    this.cacheManager = cacheManager;
    this.springCache = springCache;
    this.valueClass = (Class<V>) (genericArguments == null ? Object.class : genericArguments[1]);
  }

  @Override
  public V get(K key) {
    checkClosed();
    return springCache.get(key, valueClass);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<K, V> getAll(Set<? extends K> keys) {
    checkClosed();
    Map<K, V> values = new HashMap<>();
    for (K key : keys) {
      ValueWrapper valueWrapper = springCache.get(key);
      if (valueWrapper != null) {
        V value = (V) valueWrapper.get();
        values.put(key, value);
      }
    }
    return values;
  }

  @Override
  public boolean containsKey(K key) {
    checkClosed();
    return springCache.get(key) != null;
  }

  @Override
  public void loadAll(Set<? extends K> keys, boolean replaceExistingValues,
      CompletionListener completionListener) {
    checkClosed();
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(K key, V value) {
    checkClosed();
    springCache.put(key, value);
  }

  @Override
  public V getAndPut(K key, V value) {
    checkClosed();
    V oldValue = springCache.get(key, valueClass);
    springCache.put(key, value);
    return oldValue;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> map) {
    checkClosed();
    for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public boolean putIfAbsent(K key, V value) {
    checkClosed();
    ValueWrapper valueWrapper = springCache.get(key);
    if (valueWrapper == null) {
      put(key, value);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean remove(K key) {
    checkClosed();
    if (springCache.get(key) != null) {
      springCache.evict(key);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(K key, V oldValue) {
    checkClosed();
    if (Objects.equals(get(key), oldValue)) {
      springCache.evict(key);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public V getAndRemove(K key) {
    checkClosed();
    if (containsKey(key)) {
      V oldValue = get(key);
      remove(key);
      return oldValue;
    } else {
      return null;
    }
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    checkClosed();
    ValueWrapper valueWrapper = springCache.get(key);
    if (valueWrapper == null) {
      return false;
    } else {
      if (Objects.equals(oldValue, valueWrapper.get())) {
        put(key, newValue);
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public boolean replace(K key, V value) {
    checkClosed();
    if (containsKey(key)) {
      put(key, value);
      return true;
    } else {
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public V getAndReplace(K key, V value) {
    checkClosed();
    ValueWrapper valueWrapper = springCache.get(key);
    if (valueWrapper == null) {
      return null;
    } else {
      put(key, value);
      return (V) valueWrapper.get();
    }
  }

  @Override
  public void removeAll(Set<? extends K> keys) {
    checkClosed();
    for (K key : keys) {
      springCache.evict(key);
    }
  }

  @Override
  public void removeAll() {
    checkClosed();
    springCache.clear();
  }

  @Override
  public void clear() {
    removeAll();
  }

  @Override
  public <C extends Configuration<K, V>> C getConfiguration(Class<C> clazz) {
    checkClosed();
    throw new IllegalArgumentException();
  }

  @Override
  public <T> T invoke(K key, EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
    checkClosed();
    return entryProcessor.process(new CacheEntry(key, get(key)), arguments);
  }

  @Override
  public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> keys,
      EntryProcessor<K, V, T> entryProcessor, Object... arguments) {
    checkClosed();
    Map<K, EntryProcessorResult<T>> ret = new HashMap<>();
    for (K key : keys) {
      try {
        T entryProcessorProcessResult = invoke(key, entryProcessor, arguments);
        if (entryProcessorProcessResult != null) {
          ret.put(key, new EntryProcessorResult<T>() {
            @Override
            public T get() {
              return entryProcessorProcessResult;
            }
          });
        }
      } catch (EntryProcessorException e) {
        ret.put(key, new EntryProcessorResult<T>() {

          @Override
          public T get() {
            throw e;
          }

        });
      }
    }
    return ret;
  }

  @Override
  public String getName() {
    return springCache.getName();
  }

  @Override
  public CacheManager getCacheManager() {
    return cacheManager;
  }

  @Override
  public void close() {
    closed.set(true);
    cacheManager.destroyCache(this.getName());
  }

  @Override
  public boolean isClosed() {
    return closed.get();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T unwrap(Class<T> clazz) {
    checkClosed();
    if (clazz.isInstance(springCache)) {
      return (T) springCache;
    }
    if (springCache.getNativeCache() != null && clazz.isInstance(springCache.getNativeCache())) {
      return (T) springCache.getNativeCache();
    }
    throw new IllegalArgumentException();
  }

  @Override
  public void registerCacheEntryListener(
      CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
    checkClosed();
    throw new UnsupportedOperationException();
  }

  @Override
  public void deregisterCacheEntryListener(
      CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {
    registerCacheEntryListener(cacheEntryListenerConfiguration);
  }

  @Override
  public Iterator<javax.cache.Cache.Entry<K, V>> iterator() {
    checkClosed();
    throw new UnsupportedOperationException();
  }

  private void checkClosed() {
    if (closed.get()) {
      throw new SysException("缓存已关闭。");
    }
  }

  private class CacheEntry implements MutableEntry<K, V> {
    private K key;
    private V value;

    public CacheEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
      throw new IllegalArgumentException();
    }

    @Override
    public boolean exists() {
      return springCache.get(key) != null;
    }

    @Override
    public void remove() {
      springCache.evict(key);
    }

    @Override
    public void setValue(V value) {
      springCache.put(key, value);
    }
  }
}
