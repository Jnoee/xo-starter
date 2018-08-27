package com.github.jnoee.xo.mvc.config;

import com.github.jnoee.xo.mvc.freemarker.AbstractFreeMarkerSettings;
import com.github.jnoee.xo.utils.DateUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * FreeMarker配置组件。
 */
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
  /**
   * 构造方法。
   */
  public FreeMarkerSettings() {
    addTemplatePath("classpath:/META-INF/su/mvc/macro/");
    addTemplatePath("classpath:/META-INF/su/mvc/template/");
    addAutoImport("s", "mvc.ftl");
    addStaticClass(StringUtils.class, DateUtils.class);
  }
}
