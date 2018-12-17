package com.github.jnoee.xo.shiro.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jnoee.xo.shiro.config.ShiroProperties;
import com.github.jnoee.xo.utils.CollectionUtils;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * 登录组件。
 */
public class AuthRealm extends AuthorizingRealm {
  @Autowired
  private AuthUserService<? extends AuthUser> authUserService;
  @Autowired
  private ShiroProperties shiroProperties;

  /**
   * 检查密码是否正确。
   * 
   * @param password 原密码
   * @param hashedPassword 加密后的密码
   * @return 如果密码正确返回true，否则返回false。
   */
  public Boolean checkPassword(String password, String hashedPassword) {
    return encryptPassword(password).equals(hashedPassword);
  }

  /**
   * 加密。
   * 
   * @param password 待加密的密码
   * @return 返回加密后的密码。
   */
  public String encryptPassword(String password) {
    SimpleHash hash =
        new SimpleHash(shiroProperties.getAlgorithmName(), password, getSaltByteSource());
    if (shiroProperties.getHexEncode()) {
      return hash.toHex();
    } else {
      return hash.toBase64();
    }
  }

  @Override
  protected void onInit() {
    HashedCredentialsMatcher credentialsMatcher =
        new HashedCredentialsMatcher(shiroProperties.getAlgorithmName());
    credentialsMatcher.setStoredCredentialsHexEncoded(shiroProperties.getHexEncode());
    setCredentialsMatcher(credentialsMatcher);
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

    return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getSaltByteSource(),
        getName());
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    List<String> privilegs = authUserService.getPrivilegs();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.addRoles(getRoles(privilegs));
    info.addStringPermissions(privilegs);
    return info;
  }

  /**
   * 获取角色列表。
   * 
   * @return 返回角色列表
   */
  private List<String> getRoles(List<String> privilegs) {
    List<String> roles = new ArrayList<>();
    for (String privileg : privilegs) {
      roles.add(StringUtils.substringBefore(privileg, ":"));
    }
    CollectionUtils.distinct(roles);
    return roles;
  }

  /**
   * 获取盐值字节源。
   * 
   * @return 返回盐值字节源。
   */
  private ByteSource getSaltByteSource() {
    if (shiroProperties.getSalted()) {
      return ByteSource.Util.bytes(shiroProperties.getSalt());
    } else {
      return null;
    }
  }
}
