package com.github.jnoee.su.dwz.config;

import org.apache.shiro.SecurityUtils;

import com.github.jnoee.xo.mvc.freemarker.AbstractFreeMarkerSettings;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/su/dwz/template/");
    addTemplatePath("classpath:/META-INF/su/dwz/macro/");
    addAutoImport("dwz", "dwz.ftl");

    addStaticClass(SecurityUtils.class);
    addTld("/META-INF/su/tld/shiro.tld");
    addAutoInclude("shiro-include.ftl");
    addAutoImport("sec", "sec.ftl");
  }
}
