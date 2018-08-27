package com.github.jnoee.xo.jpa.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 该类实现动态生成Dao组件。
 */
@Slf4j
public class DaoRegistrar implements ImportBeanDefinitionRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    for (Class<?> entityType : getEntityClasses(metadata)) {
      AnnotatedGenericBeanDefinition daoDefinition = genDaoDefinition(entityType);
      String daoBeanName = genDaoBeanName(entityType);
      registry.registerBeanDefinition(daoBeanName, daoDefinition);
      log.info("自动为实体 [{}] 生成Dao组件 [{}]。", entityType.getSimpleName(), daoBeanName);
    }
  }

  /**
   * 生成DAO组件定义。
   * 
   * @param entityClass 实体类
   * @return 返回DAO组件定义。
   */
  private AnnotatedGenericBeanDefinition genDaoDefinition(Class<?> entityClass) {
    AnnotatedGenericBeanDefinition daoDefinition = new AnnotatedGenericBeanDefinition(Dao.class);
    ConstructorArgumentValues av = new ConstructorArgumentValues();
    av.addGenericArgumentValue(entityClass);
    daoDefinition.setConstructorArgumentValues(av);
    return daoDefinition;
  }

  /**
   * 生成DAO组件名称。
   * 
   * @param entityClass 实体类
   * @return 返回DAO组件名称。
   */
  private String genDaoBeanName(Class<?> entityClass) {
    String beanName = entityClass.getSimpleName();
    char[] chars = beanName.toCharArray();
    chars[0] = Character.toLowerCase(chars[0]);
    return new String(chars) + "Dao";
  }

  /**
   * 获取实体类列表。
   * 
   * @param metadata 注解元数据
   * @return 返回实体类列表。
   */
  private List<Class<?>> getEntityClasses(AnnotationMetadata metadata) {
    Set<String> packages = getPackagesToScan(metadata);
    return com.github.jnoee.xo.utils.ClassUtils.findClassesByAnnotationClass(Entity.class,
        packages.toArray(new String[0]));
  }

  /**
   * 获取扫描包名集合。
   * 
   * @param metadata 注解元数据
   * @return 返回扫描包名集合。
   */
  private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
    AnnotationAttributes attributes =
        AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EntityScan.class.getName()));
    Assert.state(attributes != null, "@DaoScan必须和@EntityScan一起使用。");
    String[] basePackages = attributes.getStringArray("basePackages");
    Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
    Set<String> packagesToScan = new LinkedHashSet<>();
    packagesToScan.addAll(Arrays.asList(basePackages));
    for (Class<?> basePackageClass : basePackageClasses) {
      packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
    }
    if (packagesToScan.isEmpty()) {
      String packageName = ClassUtils.getPackageName(metadata.getClassName());
      Assert.state(!StringUtils.isEmpty(packageName), "@DaoScan不能应用于默认包。");
      return Collections.singleton(packageName);
    }
    return packagesToScan;
  }
}
