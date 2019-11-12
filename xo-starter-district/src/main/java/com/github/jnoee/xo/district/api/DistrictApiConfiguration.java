package com.github.jnoee.xo.district.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DistrictApiConfiguration {
  @Bean
  DistrictApi districtApi() {
    return new DistrictApi();
  }
}
