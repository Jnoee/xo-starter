package com.github.jnoee.xo.jpa.search.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.core.type.AnnotationMetadata;

import com.github.jnoee.xo.jpa.dao.DaoRegistrar;

/**
 * 该类实现动态生成Dao和FullTextDao组件。
 */
public class FullTextDaoRegistrar extends DaoRegistrar {
  @Override
  protected Set<String> getPackagesToScan(AnnotationMetadata metadata) {
    return getPackagesToScan(metadata, FullTextDaoScan.class);
  }

  @Override
  protected List<Class<?>> findEntityClasses(AnnotationMetadata metadata) {
    return findClassesByAnnotationClass(metadata, FullTextDaoScan.class, Entity.class);
  }

  @Override
  protected AnnotatedGenericBeanDefinition genDaoDefinition(Class<?> entityClass) {
    if (entityClass.isAnnotationPresent(Indexed.class)) {
      AnnotatedGenericBeanDefinition daoDefinition =
          new AnnotatedGenericBeanDefinition(FullTextDao.class);
      ConstructorArgumentValues av = new ConstructorArgumentValues();
      av.addGenericArgumentValue(entityClass);
      daoDefinition.setConstructorArgumentValues(av);
      return daoDefinition;
    } else {
      return super.genDaoDefinition(entityClass);
    }
  }
}
