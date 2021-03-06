package com.github.jnoee.xo.ienum.api;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import com.github.jnoee.xo.ienum.IEnum;
import com.github.jnoee.xo.registrar.PackageScanRegistrar;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态生成枚举管理组件并注册到容器。
 */
@Slf4j
public class IEnumManagerRegistrar implements PackageScanRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    GenericBeanDefinition beanDefinition = genBeanDefinition(metadata);
    registry.registerBeanDefinition(IEnumManager.class.getSimpleName(), beanDefinition);
    log.info("生成增强枚举管理组件[{}]。", IEnumManager.class.getSimpleName());
  }

  /**
   * 生成组件定义。
   * 
   * @param metadata 注解元数据
   * @return 返回组件定义。
   */
  protected GenericBeanDefinition genBeanDefinition(AnnotationMetadata metadata) {
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(IEnumManager.class);
    beanDefinition.setLazyInit(false);
    beanDefinition.setAbstract(false);
    beanDefinition.setAutowireCandidate(true);

    MutablePropertyValues values = new MutablePropertyValues();
    values.add("ienumClasses",
        findClassesByParentClass(metadata, EnableIEnumApi.class, IEnum.class));
    beanDefinition.setPropertyValues(values);

    return beanDefinition;
  }
}
