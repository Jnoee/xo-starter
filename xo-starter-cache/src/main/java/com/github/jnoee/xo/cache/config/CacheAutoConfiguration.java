package com.github.jnoee.xo.cache.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.github.jnoee.xo.cache.GenericCacheManager;

@Configuration
@EnableCaching
@EnableRedissonHttpSession
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {
  @Autowired
  private CacheProperties cacheProperties;

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
    if ("fst".equals(cacheProperties.getSerializer())) {
      config.setCodec(new FstCodec());
    }
    return Redisson.create(config);
  }

  @Bean
  CacheManager cacheManager(RedissonClient redissonClient) {
    return new GenericCacheManager(redissonClient);
  }

  private Boolean isJsonFile(String fileName) {
    return fileName.endsWith(".json");
  }
}
