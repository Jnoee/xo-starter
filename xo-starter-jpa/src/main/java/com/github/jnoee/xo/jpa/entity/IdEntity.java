package com.github.jnoee.xo.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基于雪花算法Long类型主键的实体基类。
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public abstract class IdEntity {
  @Id
  @GeneratedValue(generator = "id-generator")
  @GenericGenerator(name = "id-generator", strategy = "com.github.jnoee.xo.jpa.entity.IdGenerator")
  protected Long id;
}
