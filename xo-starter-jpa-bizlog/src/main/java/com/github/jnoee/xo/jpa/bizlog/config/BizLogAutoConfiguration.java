package com.github.jnoee.xo.jpa.bizlog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.jpa.bizlog.aspect.DetailLogAspect;
import com.github.jnoee.xo.jpa.bizlog.aspect.SimpleLogAspect;
import com.github.jnoee.xo.jpa.bizlog.service.BizLogService;
import com.github.jnoee.xo.jpa.config.JpaAutoConfiguration;
import com.github.jnoee.xo.jpa.dao.DaoScan;

@Configuration
@AutoConfigureAfter(value = JpaAutoConfiguration.class)
@EntityScan("com.cntest.su.jpa.bizlog.entity")
@DaoScan
public class BizLogAutoConfiguration {
  @Bean
  public BizLogService bizLogService() {
    return new BizLogService();
  }

  @Bean
  public SimpleLogAspect simpleLogAspect() {
    return new SimpleLogAspect();
  }

  @Bean
  public DetailLogAspect detailLogAspect() {
    return new DetailLogAspect();
  }
}
