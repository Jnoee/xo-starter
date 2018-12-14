package com.github.jnoee.xo.kaptcha.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.kaptcha.Kaptcha;
import com.github.jnoee.xo.kaptcha.action.KaptchaAction;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@EnableConfigurationProperties(KaptchaProperties.class)
public class KaptchaAutoConfiguration {
  @Bean
  KaptchaAction kaptchaAction() {
    return new KaptchaAction();
  }

  @Bean
  Kaptcha kaptcha() {
    return new Kaptcha();
  }

  @Bean
  DefaultKaptcha defaultKaptcha(KaptchaProperties properties) {
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    Config config = new Config(properties.toProperties());
    defaultKaptcha.setConfig(config);
    return defaultKaptcha;
  }
}
