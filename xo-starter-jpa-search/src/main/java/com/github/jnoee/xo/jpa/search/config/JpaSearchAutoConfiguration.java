package com.github.jnoee.xo.jpa.search.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.jpa.search.index.EntityIndexManager;

@Configuration
@AutoConfigureAfter(value = JpaRepositoriesAutoConfiguration.class)
public class JpaSearchAutoConfiguration {
  @Bean
  EntityIndexManager entityIndexManager() {
    return new EntityIndexManager();
  }
}
