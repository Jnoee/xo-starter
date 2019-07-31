package com.github.jnoee.xo.auth.server;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jnoee.xo.auth.AuthHelper;
import com.github.jnoee.xo.auth.AuthToken;

/**
 * 认证组件。
 */
public class AuthServerRealm extends AuthorizingRealm {
  @Autowired
  private AuthUserService<? extends AuthUser> authUserService;
  @Autowired
  private AuthHelper authHelper;

  @Override
  protected void onInit() {
    setCredentialsMatcher(authHelper.createMatcher());
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
    String username = token.getPrincipal().toString();

    AuthUser user = authUserService.getByUsername(username);
    if (user == null) {
      throw new UnknownAccountException();
    }
    if (!user.getEnabled()) {
      throw new DisabledAccountException();
    }

    AuthToken authToken = authUserService.genAuthToken(username);
    return new SimpleAuthenticationInfo(authToken, user.getPassword(),
        authHelper.getSaltByteSource(), getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    AuthToken authToken = (AuthToken) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addRoles(authToken.getRoles());
    info.addStringPermissions(authToken.getPrivilegs());
    return info;
  }
}
