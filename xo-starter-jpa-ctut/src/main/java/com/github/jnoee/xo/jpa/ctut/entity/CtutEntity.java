package com.github.jnoee.xo.jpa.ctut.entity;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.StringUtils;

import com.github.jnoee.xo.jpa.entity.UuidEntity;
import com.github.jnoee.xo.shiro.auth.AuthUser;
import com.github.jnoee.xo.shiro.auth.AuthUserService;
import com.github.jnoee.xo.utils.SpringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 资源实体基类。
 * 
 * @param <U> 用户类型
 */
@MappedSuperclass
@Getter
@Setter
public abstract class CtutEntity<U extends AuthUser> extends UuidEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "createUserId")
  private U createUser;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "updateUserId")
  private U updateUser;
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateTime;

  /**
   * 自动填充创建人、创建时间、修改人、修改时间。
   */
  public void autoFillIn() {
    U user = getUserService().getDefaultUser();
    Date now = new Date();
    if (StringUtils.isEmpty(getId())) {
      setCreateUser(user);
      setCreateTime(now);
      setUpdateUser(user);
      setUpdateTime(now);
    } else {
      setUpdateUser(user);
      setUpdateTime(now);
    }
  }

  @SuppressWarnings("unchecked")
  private AuthUserService<U> getUserService() {
    return SpringUtils.getBean(AuthUserService.class);
  }
}
