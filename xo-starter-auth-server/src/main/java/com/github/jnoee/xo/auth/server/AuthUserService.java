package com.github.jnoee.xo.auth.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;

import com.github.jnoee.xo.auth.AuthToken;

/**
 * 认证服务。
 */
public interface AuthUserService<U extends AuthUser> {
  /**
   * 根据用户名获取认证用户。
   * 
   * @param username 用户名
   * @return 返回指定认证用户。
   */
  U getByUsername(String username);

  /**
   * 获取指定用户的权限列表。默认返回空列表，对于需要权限控制的应用要覆盖重写该方法。
   * 
   * @param user 用户
   * @return 返回指定用户的权限列表。
   */
  default List<String> getPrivilegs(String username) {
    return new ArrayList<>();
  }

  /**
   * 获取当前登录用户的权限列表。
   * 
   * @return 返回当前登录用户的权限列表。
   */
  default List<String> getPrivilegs() {
    return getPrivilegs(getLogonUser().getUsername());
  }

  /**
   * 登录。
   * 
   * @param username 用户名
   * @param password 密码
   */
  default void login(String username, String password) {
    AuthenticationToken token = new UsernamePasswordToken(username, password);
    Subject subject = SecurityUtils.getSubject();
    subject.login(token);
  }

  /**
   * 获取当前登录用户。
   * 
   * @return 返回当前登录用户。
   */
  default U getLogonUser() {
    try {
      AuthToken authToken = (AuthToken) SecurityUtils.getSubject().getPrincipal();
      return getByUsername(authToken.getUsername());
    } catch (Exception e) {
      throw new UnauthenticatedException("获取当前登录用户时发生异常。", e);
    }
  }

  /**
   * 获取管理员用户。
   * 
   * @return 返回管理员用户。
   */
  default U getAdminUser() {
    return getByUsername("admin");
  }

  /**
   * 获取默认操作用户。
   * 
   * @return 存在登录用户时返回登录用户，否则返回管理员用户。
   */
  default U getDefaultUser() {
    try {
      U user = getLogonUser();
      if (user == null) {
        user = getAdminUser();
      }
      return user;
    } catch (Exception e) {
      return getAdminUser();
    }
  }
}
