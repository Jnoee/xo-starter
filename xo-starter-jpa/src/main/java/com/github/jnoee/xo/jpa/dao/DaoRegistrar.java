package com.github.jnoee.xo.jpa.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.core.type.AnnotationMetadata;

import com.github.jnoee.xo.registrar.PackageScanRegistrar;

import lombok.extern.slf4j.Slf4j;

/**
 * 该类实现动态生成Dao组件。
 */
@Slf4j
public class DaoRegistrar implements PackageScanRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata metadata,
      BeanDefinitionRegistry registry) {
    EntityScanPackages.register(registry, getPackagesToScan(metadata));
    for (Class<?> entityType : findEntityClasses(metadata)) {
      AnnotatedGenericBeanDefinition daoDefinition = genDaoDefinition(entityType);
      String daoBeanName = genDaoBeanName(entityType);
      registry.registerBeanDefinition(daoBeanName, daoDefinition);
      log.info("自动为实体 [{}] 生成Dao组件 [{}]。", entityType.getSimpleName(), daoBeanName);
    }
  }

  /**
   * 获取扫描包。
   * 
   * @param metadata 注解元数据
   * @return 返回包列表。
   */
  protected Set<String> getPackagesToScan(AnnotationMetadata metadata) {
    return getPackagesToScan(metadata, DaoScan.class);
  }

  /**
   * 查找实体类列表。
   * 
   * @param metadata 注解元数据
   * @return 返回实体类列表。
   */
  protected List<Class<?>> findEntityClasses(AnnotationMetadata metadata) {
    return findClassesByAnnotationClass(metadata, DaoScan.class, Entity.class);
  }

  /**
   * 生成DAO组件定义。
   * 
   * @param entityClass 实体类
   * @return 返回DAO组件定义。
   */
  protected AnnotatedGenericBeanDefinition genDaoDefinition(Class<?> entityClass) {
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
}
