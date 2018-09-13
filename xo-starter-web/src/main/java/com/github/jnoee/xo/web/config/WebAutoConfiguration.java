package com.github.jnoee.xo.web.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jnoee.xo.utils.DateUtils;
import com.github.jnoee.xo.web.converter.IEnumToString;
import com.github.jnoee.xo.web.converter.StringToIEnum;
import com.github.jnoee.xo.web.handler.ErrorView;
import com.github.jnoee.xo.web.handler.WebErrorAttributes;
import com.github.jnoee.xo.web.handler.WebErrorController;
import com.github.jnoee.xo.web.jackson.IEnumModule;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(ApiDocProperties.class)
@EnableSwagger2
public class WebAutoConfiguration implements WebMvcConfigurer {
  /**
   * 配置ErrorAttributes组件。
   * 
   * @return 返回ErrorAttributes组件。
   */
  @Bean
  public ErrorAttributes errorAttributes() {
    return new WebErrorAttributes();
  }

  /**
   * 配置异常处理组件。
   * 
   * @return 返回异常处理组件。
   */
  @Bean
  public WebErrorController webErrorController() {
    return new WebErrorController();
  }

  /**
   * 配置异常视图。
   * 
   * @param objectMapper ObjectMapper
   * @param errorAttributes ErrorAttributes
   * @return 返回异常视图。
   */
  @Bean(name = "error")
  public View errorView(ObjectMapper objectMapper, ErrorAttributes errorAttributes) {
    return new ErrorView(objectMapper, errorAttributes);
  }

  /**
   * 配置IEnum Jackson转换组件。
   * 
   * @return 返回IEnum Jackson转换组件。
   */
  @Bean
  public IEnumModule ienumModule() {
    return new IEnumModule();
  }

  /**
   * 配置跨域支持组件。
   * 
   * @return 返回跨域支持组件。
   */
  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addExposedHeader("x-auth-token");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  /**
   * 配置API接口文档支持组件。
   * 
   * @return 返回API接口文档支持组件。
   */
  @Bean
  public Docket docket(ApiDocProperties prop) {
    Docket apiDocket = new Docket(DocumentationType.SWAGGER_2);
    ApiInfo apiInfo = new ApiInfoBuilder().title(prop.getTitle()).description(prop.getDescription())
        .version(prop.getVersion()).build();
    apiDocket.apiInfo(apiInfo);
    setDocketSecurity(apiDocket);
    return apiDocket.select().apis(RequestHandlerSelectors.basePackage(prop.getBasePackage()))
        .paths(PathSelectors.any()).build();
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    // 添加默认java.util.Date转换器
    registry.addFormatter(new DateFormatter(DateUtils.DAY));
    // 添加DateTimeFormat注解的日期时间转换器
    registry.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
    registry.addFormatterForFieldAnnotation(new JodaDateTimeFormatAnnotationFormatterFactory());

    registry.removeConvertible(String.class, Enum.class);
    registry.addConverterFactory(new StringToIEnum());
    registry.addConverter(new IEnumToString());
  }

  /**
   * 配置API接口文档组件x-auth-token安全设置。
   * 
   * @param apiDocket API接口文档组件
   */
  private void setDocketSecurity(Docket apiDocket) {
    ApiKey apiKey = new ApiKey("Authorization", "x-auth-token", "header");
    apiDocket.securitySchemes(Lists.newArrayList(apiKey));

    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    List<SecurityReference> securityReferences = Lists.newArrayList(
        new SecurityReference("Authorization", new AuthorizationScope[] {authorizationScope}));
    SecurityContext securityContext =
        SecurityContext.builder().securityReferences(securityReferences).build();
    apiDocket.securityContexts(Lists.newArrayList(securityContext));
  }
}
