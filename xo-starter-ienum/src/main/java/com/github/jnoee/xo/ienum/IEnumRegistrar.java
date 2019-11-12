package com.github.jnoee.xo.ienum;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import com.github.jnoee.xo.registrar.PackageScanRegistrar;

import lombok.extern.slf4j.Slf4j;

/**
 * 该类实现动态生成Dao组件。
 */
@Slf4j
public class IEnumRegistrar implements PackageScanRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    List<Class<?>> ienumClasses = findClassesByParentClass(metadata, IEnumScan.class, IEnum.class);
    ienumClasses.forEach(ienumClass -> {
      log.info("扫描到自定义枚举类[{}]", ienumClass.getSimpleName());
      IEnumManager.add(ienumClass);
    });
  }
}
