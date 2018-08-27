package com.github.jnoee.xo.jpa.ctut.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.jpa.config.JpaAutoConfiguration;
import com.github.jnoee.xo.jpa.ctut.aspect.CtutAspect;

@Configuration
@AutoConfigureAfter(value = JpaAutoConfiguration.class)
public class CtutAutoConfiguration {
  @Bean
  public CtutAspect ctutAspect() {
    return new CtutAspect();
  }
}
