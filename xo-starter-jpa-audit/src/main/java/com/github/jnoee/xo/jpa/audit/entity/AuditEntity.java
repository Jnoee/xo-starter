package com.github.jnoee.xo.jpa.audit.entity;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.github.jnoee.xo.auth.server.AuthUser;
import com.github.jnoee.xo.jpa.entity.IdEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 审计实体基类。
 * 
 * @param <U> 用户类型
 */
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity<U extends AuthUser> extends IdEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "createUserId")
  @CreatedBy
  private U createUser;
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createTime;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "updateUserId")
  @LastModifiedBy
  private U updateUser;
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updateTime;
}
