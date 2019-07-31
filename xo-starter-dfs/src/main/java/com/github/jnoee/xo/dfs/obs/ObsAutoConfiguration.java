package com.github.jnoee.xo.dfs.obs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.dfs.DfsClient;

@Configuration
@EnableConfigurationProperties(ObsProperties.class)
@ConditionalOnProperty(name = "xo.dfs.type", havingValue = "obs", matchIfMissing = false)
public class ObsAutoConfiguration {
  @Bean
  DfsClient obsClient() {
    return new ObsDfsClient();
  }
}
