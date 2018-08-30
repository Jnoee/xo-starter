package com.github.jnoee.xo.cache.redis.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.github.jnoee.xo.cache.redis.GenericRedisCacheManager;

@Configuration
@EnableCaching
@EnableRedisHttpSession
@EnableConfigurationProperties(RedisCacheProperties.class)
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired
  private RedisCacheProperties cacheProperties;

  @Bean
  @ConditionalOnProperty(name = "xo.cache.redis.x-auth-token", havingValue = "true",
      matchIfMissing = true)
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    RedisCacheWriter redisCacheWriter =
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(cacheProperties.getTtl()));
    if (!cacheProperties.getCachingNullValues()) {
      defaultCacheConfig = defaultCacheConfig.disableCachingNullValues();
    }

    return new GenericRedisCacheManager(redisCacheWriter, defaultCacheConfig);
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (Object o, Method method, Object... args) -> {
      StringBuilder build = new StringBuilder();
      build.append(o.getClass().getName());
      build.append("#");
      build.append(method.getName());
      build.append("(");
      if (args.length > 0) {
        for (Object arg : args) {
          build.append(arg.toString()).append(",");
        }
        build.deleteCharAt(build.length() - 1);
      }
      build.append(")");
      return build.toString();
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
