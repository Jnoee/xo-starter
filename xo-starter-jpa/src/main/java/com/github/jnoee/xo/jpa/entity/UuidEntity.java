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
 * 基于UUID主键的实体基类。
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public abstract class UuidEntity {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
  protected String id;
}
