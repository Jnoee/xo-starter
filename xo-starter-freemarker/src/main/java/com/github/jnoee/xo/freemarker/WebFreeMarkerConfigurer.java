package com.github.jnoee.xo.freemarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import lombok.Getter;

/**
 * web环境下的FreeMarker配置组件。
 */
public class WebFreeMarkerConfigurer extends FreeMarkerConfigurer
    implements GenericFreeMarkerConfigurer {
  private ServletContext servletContext;
  @Getter
  private ApplicationContext context;
  @Getter
  private List<AbstractFreeMarkerSettings> settings = new ArrayList<>();

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    context = applicationContext;
    Map<String, AbstractFreeMarkerSettings> freemarkerSettingsMap =
        context.getBeansOfType(AbstractFreeMarkerSettings.class);
    settings.addAll(freemarkerSettingsMap.values());
    Collections.sort(settings);
  }

  @Override
  public void setServletContext(ServletContext servletContext) {
    super.setServletContext(servletContext);
    this.servletContext = servletContext;
  }

  @Override
  public void afterPropertiesSet() throws IOException, TemplateException {
    super.afterPropertiesSet();
    init();
    initCtx();
    initTlds();
  }

  @Override
  protected void postProcessTemplateLoaders(List<TemplateLoader> templateLoaders) {
    super.postProcessTemplateLoaders(templateLoaders);
    for (String templatePath : getTemplatePaths()) {
      templateLoaders.add(getTemplateLoaderForPath(templatePath));
    }
  }

  /**
   * 初始化上下文全局变量。
   * 
   * @throws TemplateModelException 初始化上下文全局变量失败时抛出异常。
   */
  protected void initCtx() throws TemplateModelException {
    getConfiguration().setSharedVariable("ctx", servletContext.getContextPath());
  }

  /**
   * 初始化TLD文件。
   */
  protected void initTlds() {
    List<String> tlds = new ArrayList<>();
    for (AbstractFreeMarkerSettings freeMarkerSettings : getSettings()) {
      tlds.addAll(freeMarkerSettings.getTlds());
    }
    getTaglibFactory().setClasspathTlds(tlds);
  }
}
