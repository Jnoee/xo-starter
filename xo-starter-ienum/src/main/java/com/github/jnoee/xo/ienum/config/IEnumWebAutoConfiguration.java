package com.github.jnoee.xo.ienum.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jnoee.xo.ienum.converter.IEnumToString;
import com.github.jnoee.xo.ienum.converter.StringToIEnum;

/**
 * 枚举增强web配置。
 */
@Configuration
@ConditionalOnWebApplication
public class IEnumWebAutoConfiguration implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.removeConvertible(String.class, Enum.class);
    registry.addConverterFactory(new StringToIEnum());
    registry.addConverter(new IEnumToString());
  }
}
