package com.github.jnoee.xo.cache.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.github.jnoee.xo.cache.redis.GenericRedisCacheManager;
import com.github.jnoee.xo.cache.redis.serializer.SerializerFactory;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisCacheProperties.class)
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired
  private RedisCacheProperties cacheProperties;

  @Bean
  RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setDefaultSerializer(SerializerFactory.create(cacheProperties.getSerializer()));
    return redisTemplate;
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    RedisCacheWriter redisCacheWriter =
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(cacheProperties.getTtl()))
        .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(SerializationPair
            .fromSerializer(SerializerFactory.create(cacheProperties.getSerializer())));
    if (!cacheProperties.getCachingNullValues()) {
      defaultCacheConfig = defaultCacheConfig.disableCachingNullValues();
    }

    return new GenericRedisCacheManager(redisCacheWriter, defaultCacheConfig);
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, objects) -> {
      StringBuilder key = new StringBuilder();
      key.append(target.getClass().getName());
      key.append("::" + method.getName() + ":");
      for (Object obj : objects) {
        key.append(obj.toString());
      }
      return key.toString();
    };
  }

  @Bean
  @Override
  public CacheResolver cacheResolver() {
    return new SimpleCacheResolver(cacheManager());
  }

  @Bean
  @Override
  public CacheErrorHandler errorHandler() {
    return new SimpleCacheErrorHandler();
  }
}
