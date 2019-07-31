package com.github.jnoee.xo.id.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.id.IdGeneratorFactoryBean;

/**
 * 分布式主键生成器配置。
 */
@Configuration
@EnableConfigurationProperties(IdProperties.class)
public class IdAutoConfiguration {
  @Bean
  IdGeneratorFactoryBean idGenerator() {
    return new IdGeneratorFactoryBean();
  }
}
