package com.github.jnoee.xo.auth.feign.handle;

import org.springframework.web.context.request.RequestContextHolder;

import com.github.jnoee.xo.auth.constant.TokenIds;
import com.github.jnoee.xo.utils.StringUtils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign认证拦截器。
 */
public class AuthRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate template) {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    if (StringUtils.isNotBlank(sessionId)) {
      template.header(TokenIds.X_AUTH_TOKEN, sessionId);
    }
  }
}
