package com.github.jnoee.xo.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationToken;

import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 认证令牌。
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken implements AuthenticationToken {
  private static final long serialVersionUID = -6044658837142042956L;
  /** 用户名 */
  private String username;
  /** 权限列表 */
  private List<String> privilegs = new ArrayList<>();

  public List<String> getRoles() {
    List<String> roles = new ArrayList<>();
    for (String privileg : privilegs) {
      roles.add(StringUtils.substringBefore(privileg, ":"));
    }
    CollectionUtils.distinct(roles);
    return roles;
  }

  @Override
  public Object getPrincipal() {
    return this;
  }

  @Override
  public Object getCredentials() {
    return Boolean.TRUE;
  }
}
