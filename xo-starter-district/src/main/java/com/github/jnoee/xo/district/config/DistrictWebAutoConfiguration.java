package com.github.jnoee.xo.district.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jnoee.xo.district.converter.DistrictToString;
import com.github.jnoee.xo.district.converter.StringToDistrict;

@Configuration
@ConditionalOnWebApplication
public class DistrictWebAutoConfiguration implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverterFactory(new StringToDistrict());
    registry.addConverter(new DistrictToString());
  }
}
