package com.github.jnoee.xo.cache.config;

import com.github.jnoee.xo.cache.GenericCacheManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.config.Config;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.io.IOException;

@Configuration
@EnableCaching
@EnableRedissonHttpSession
@EnableConfigurationProperties(CacheProperties.class)
public class CacheAutoConfiguration {
    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    @ConditionalOnProperty(name = "xo.cache.x-auth-token", havingValue = "true",
            matchIfMissing = true)
    HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson(ResourcePatternResolver resolver) throws IOException {
        String configFile = cacheProperties.getConfigFile();
        Resource resource = resolver.getResource(configFile);
        Config config = null;
        if (isJsonFile(configFile)) {
            config = Config.fromJSON(resource.getInputStream());
        } else {
            config = Config.fromYAML(resource.getInputStream());
        }
        config.setCodec(new FstCodec());
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
