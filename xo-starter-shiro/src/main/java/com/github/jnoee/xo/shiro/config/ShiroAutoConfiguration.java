package com.github.jnoee.xo.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.shiro.auth.AuthRealm;
import com.github.jnoee.xo.shiro.auth.AuthUserService;
import com.github.jnoee.xo.shiro.handle.AuthErrorController;

/**
 * 组件配置。
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean({AuthUserService.class})
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {
  /**
   * 配置AuthRealm组件。
   * 
   * @return 返回AuthRealm组件。
   */
  @Bean
  public AuthRealm authRealm() {
    return new AuthRealm();
  }

  /**
   * 配置SecurityManager组件。
   * 
   * @return 返回SecurityManager组件。
   */
  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(authRealm());
    return securityManager;
  }

  /**
   * 配置DefaultAdvisorAutoProxyCreator组件。
   * 
   * @return 返回DefaultAdvisorAutoProxyCreator组件。
   */
  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator =
        new DefaultAdvisorAutoProxyCreator();
    defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    return defaultAdvisorAutoProxyCreator;
  }

  /**
   * 配置AuthorizationAttributeSourceAdvisor组件。
   * 
   * @param securityManager SecurityManager组件
   * @return 返回AuthorizationAttributeSourceAdvisor组件。
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    advisor.setSecurityManager(securityManager);
    return advisor;
  }

  /**
   * 配置LifecycleBeanPostProcessor组件。
   * 
   * @return 返回LifecycleBeanPostProcessor组件。
   */
  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 配置ShiroFilter组件。
   * 
   * @return 返回ShiroFilter组件。
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
    factory.setSecurityManager(securityManager);
    factory.setUnauthorizedUrl("/401");
    return factory;
  }

  /**
   * 配置权限相关异常处理组件。
   * 
   * @return 返回权限相关异常处理组件。
   */
  @Bean
  public AuthErrorController authErrorController() {
    return new AuthErrorController();
  }
}
