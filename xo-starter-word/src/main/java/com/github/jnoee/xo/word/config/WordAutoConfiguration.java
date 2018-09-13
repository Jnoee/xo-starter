package com.github.jnoee.xo.word.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.word.WordFactory;

@Configuration
@EnableConfigurationProperties(WordProperties.class)
public class WordAutoConfiguration {
  @Bean
  public WordFactory wordFactory() {
    return new WordFactory();
  }
}
