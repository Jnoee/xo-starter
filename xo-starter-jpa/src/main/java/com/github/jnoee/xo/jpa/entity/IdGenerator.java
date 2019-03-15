package com.github.jnoee.xo.jpa.entity;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.github.jnoee.xo.utils.SpringUtils;

import io.shardingsphere.core.keygen.KeyGenerator;

/**
 * 基于雪花算法分布式ID生成器。
 */
public class IdGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) {
    return getKeyGenerator().generateKey();
  }

  private KeyGenerator getKeyGenerator() {
    return SpringUtils.getBean(KeyGenerator.class);
  }
}
