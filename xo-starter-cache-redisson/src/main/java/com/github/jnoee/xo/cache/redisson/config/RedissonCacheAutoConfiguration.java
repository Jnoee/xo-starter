package com.github.jnoee.xo.cache.redisson.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.github.jnoee.xo.cache.CacheSerializer;
import com.github.jnoee.xo.cache.redisson.GenericRedissonCacheManager;
import com.github.jnoee.xo.exception.SysException;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedissonCacheProperties.class)
public class RedissonCacheAutoConfiguration {
  @Autowired
  private RedissonCacheProperties cacheProperties;

  @Bean(destroyMethod = "shutdown")
  RedissonClient redisson() throws IOException {
    String configFile = cacheProperties.getConfigFile();
    ClassPathResource resource = new ClassPathResource(configFile);
    Config config = null;
    if (isJsonFile(configFile)) {
      config = Config.fromJSON(resource.getInputStream());
    } else {
      config = Config.fromYAML(resource.getInputStream());
    }
    config.setCodec(getCodec(cacheProperties.getSerializer()));
    return Redisson.create(config);
  }

  @Bean
  CacheManager cacheManager(RedissonClient redissonClient) {
    return new GenericRedissonCacheManager(redissonClient);
  }

  private Codec getCodec(CacheSerializer type) {
    switch (type) {
      case JAVA:
        return new SerializationCodec(getClass().getClassLoader());
      case JSON:
        return new JsonJacksonCodec(getClass().getClassLoader());
      case FST:
        return new FstCodec(getClass().getClassLoader());
      default:
        throw new SysException("没有匹配的序列化器。");
    }
  }

  private Boolean isJsonFile(String fileName) {
    return fileName.endsWith(".json");
  }
}
