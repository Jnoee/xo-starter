package com.github.jnoee.xo.dfs.oss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.dfs.DfsClient;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(name = "xo.dfs.type", havingValue = "oss", matchIfMissing = false)
public class OssAutoConfiguration {
  @Bean
  DfsClient ossClient() {
    return new OssDfsClient();
  }
}
