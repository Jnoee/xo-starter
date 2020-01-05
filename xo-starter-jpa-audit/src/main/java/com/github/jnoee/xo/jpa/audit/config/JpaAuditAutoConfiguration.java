package com.github.jnoee.xo.jpa.audit.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.audit.aspect.DetailLogAspect;
import com.github.jnoee.xo.jpa.audit.aspect.SimpleLogAspect;
import com.github.jnoee.xo.jpa.audit.handle.GenericAuditorAware;
import com.github.jnoee.xo.jpa.audit.service.BizLogService;
import com.github.jnoee.xo.jpa.config.JpaAutoConfiguration;
import com.github.jnoee.xo.jpa.search.dao.FullTextDaoScan;

@Configuration
@AutoConfigureAfter(value = JpaAutoConfiguration.class)
@EnableJpaAuditing
@FullTextDaoScan("com.github.jnoee.xo.jpa.audit.entity")
public class JpaAuditAutoConfiguration {
  @Bean
  AuditorAware<AuthUser> auditorAware() {
    return new GenericAuditorAware();
  }

  @Bean
  BizLogService bizLogService() {
    return new BizLogService();
  }

  @Bean
  SimpleLogAspect simpleLogAspect() {
    return new SimpleLogAspect();
  }

  @Bean
  DetailLogAspect detailLogAspect() {
    return new DetailLogAspect();
  }
}
