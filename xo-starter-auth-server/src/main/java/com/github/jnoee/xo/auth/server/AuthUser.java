package com.github.jnoee.xo.auth.server;

/**
 * 认证用户。
 */
public interface AuthUser {
  /**
   * 获取用户名。
   * 
   * @return 返回用户名。
   */
  String getUsername();

  /**
   * 获取密码。
   * 
   * @return 返回密码。
   */
  String getPassword();

  /**
   * 获取是否可用。
   * 
   * @return 返回是否可用。
   */
  Boolean getEnabled();
}
