package com.github.jnoee.xo.jpa.audit.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.github.jnoee.xo.jpa.audit.handle.GenericAuditorAware;
import com.github.jnoee.xo.jpa.config.JpaAutoConfiguration;
import com.github.jnoee.xo.shiro.auth.AuthUser;

@Configuration
@AutoConfigureAfter(value = JpaAutoConfiguration.class)
@EnableJpaAuditing
public class JpaAuditAutoConfiguration {
  @Bean
  public AuditorAware<AuthUser> auditorAware() {
    return new GenericAuditorAware();
  }
}
