package com.github.jnoee.xo.cache.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.github.jnoee.xo.cache.AbstractCacheSettings;
import com.github.jnoee.xo.cache.CacheRegion;
import com.github.jnoee.xo.cache.redis.config.RedisCacheProperties;
import com.github.jnoee.xo.cache.redis.serializer.SerializerFactory;

/**
 * 自定义RedisCacheManager。从AbstractCacheSettings组件实例中获取缓存配置。
 */
public class GenericRedisCacheManager extends RedisCacheManager implements ApplicationContextAware {
  @Autowired
  private RedisCacheProperties cacheProperties;
  private List<AbstractCacheSettings> settings = new ArrayList<>();

  public GenericRedisCacheManager(RedisCacheWriter cacheWriter,
      RedisCacheConfiguration defaultCacheConfiguration) {
    super(cacheWriter, defaultCacheConfiguration);
  }

  @Override
  public void setApplicationContext(ApplicationContext context) {
    Map<String, AbstractCacheSettings> freemarkerSettingsMap =
        context.getBeansOfType(AbstractCacheSettings.class);
    settings.addAll(freemarkerSettingsMap.values());
  }

  @Override
  protected Collection<RedisCache> loadCaches() {
    Collection<RedisCache> caches = super.loadCaches();
    List<CacheRegion> regions = new ArrayList<>();
    for (AbstractCacheSettings setting : settings) {
      regions.addAll(setting.getRegions());
    }
    for (CacheRegion region : regions) {
      RedisCacheConfiguration cacheConfig = createRedisCacheConfiguration(region);
      caches.add(createRedisCache(region.getName(), cacheConfig));
    }
    return caches;
  }

  /**
   * 创建Redis缓存配置。
   * 
   * @param cacheRegion 缓存分区配置
   * @return 返回Redis缓存配置。
   */
  private RedisCacheConfiguration createRedisCacheConfiguration(CacheRegion cacheRegion) {
    RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(cacheRegion.getTtl()))
        .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(SerializationPair
            .fromSerializer(SerializerFactory.create(cacheProperties.getSerializer())));
    if (!cacheRegion.getCachingNullValues()) {
      cacheConfig = cacheConfig.disableCachingNullValues();
    }
    return cacheConfig;
  }
}
