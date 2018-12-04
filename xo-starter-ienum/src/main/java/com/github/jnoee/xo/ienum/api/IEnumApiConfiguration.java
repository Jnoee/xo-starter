package com.github.jnoee.xo.ienum.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IEnumApiConfiguration {
  @Bean
  IEnumApi ienumApi() {
    return new IEnumApi();
  }
}
