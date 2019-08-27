package com.github.jnoee.xo.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

public interface SessionConfigurationAware {
  @Bean
  @ConditionalOnProperty(name = "xo.session.token", havingValue = "true", matchIfMissing = true)
  default HttpSessionIdResolver httpSessionIdResolver(SessionProperties sessionProperties) {
    return new HeaderHttpSessionIdResolver(sessionProperties.getTokenName());
  }
}
