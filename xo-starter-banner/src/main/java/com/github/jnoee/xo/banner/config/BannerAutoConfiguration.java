package com.github.jnoee.xo.banner.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BannerProperties.class)
public class BannerAutoConfiguration {
}
