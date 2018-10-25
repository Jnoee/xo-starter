package com.github.jnoee.xo.auth.server;

/**
 * 认证用户。
 */
public interface AuthUser {
  /** 获取用户名 */
  String getUsername();

  /** 获取密码 */
  String getPassword();

  /** 是否可用 */
  Boolean getEnabled();
}
