package com.github.jnoee.xo.banner.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * Banner默认配置处理器。
 */
public class BannerEnvironmentPostProcessor implements EnvironmentPostProcessor {
  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {
    Properties props = new Properties();
    String banner = environment.getProperty("xo.banner.name", String.class, "xo");
    props.put("spring.banner.location", "classpath:/META-INF/xo/banner/" + banner + ".txt");
    PropertiesPropertySource propertiesPropertySource =
        new PropertiesPropertySource("bannerProperties", props);
    environment.getPropertySources().addLast(propertiesPropertySource);
  }
}
