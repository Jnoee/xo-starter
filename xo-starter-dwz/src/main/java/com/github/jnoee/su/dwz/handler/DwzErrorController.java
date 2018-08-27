package com.github.jnoee.su.dwz.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.github.jnoee.su.dwz.result.DwzResultBuild;
import com.github.jnoee.xo.exception.BizException;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.StringUtils;

@ControllerAdvice
public class DwzErrorController {
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(BindException.class)
  public ModelAndView handleBind(BindException e) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = e.getBindingResult().getAllErrors();
    for (ObjectError bindError : bindErrors) {
      errorMsgs.add(bindError.getDefaultMessage());
    }
    return new DwzResultBuild().error(messageSource.get("E990", StringUtils.join(errorMsgs, "|")))
        .buildView();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ModelAndView handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = e.getBindingResult().getAllErrors();
    for (ObjectError bindError : bindErrors) {
      errorMsgs.add(bindError.getDefaultMessage());
    }
    return new DwzResultBuild().error(messageSource.get("E990", StringUtils.join(errorMsgs, "|")))
        .buildView();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ModelAndView handleConstraintViolation(ConstraintViolationException e) {
    List<String> errorMsgs = new ArrayList<>();
    Set<ConstraintViolation<?>> bindErrors = e.getConstraintViolations();
    for (ConstraintViolation<?> bindError : bindErrors) {
      errorMsgs.add(bindError.getMessage());
    }
    return new DwzResultBuild().error(messageSource.get("E990", StringUtils.join(errorMsgs, "|")))
        .buildView();
  }

  @ExceptionHandler(BizException.class)
  public ModelAndView handleBiz(BizException e) {
    return new DwzResultBuild().error(e.getMessage()).buildView();
  }

  @ExceptionHandler(AuthorizationException.class)
  public ModelAndView handleAuthentication(HttpServletRequest request) {
    if (isAjaxRequest(request)) {
      return new DwzResultBuild().timeout().buildView();
    } else {
      return new ModelAndView("redirect:/login");
    }
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ModelAndView handleAuthorization(HttpServletRequest request) {
    if (isAjaxRequest(request)) {
      return new DwzResultBuild().denied().buildView();
    } else {
      return new ModelAndView("redirect:/login");
    }
  }

  /**
   * 判断是否AJAX请求。
   * 
   * @param request 请求对象
   * @return 返回是否AJAX请求。
   */
  private Boolean isAjaxRequest(HttpServletRequest request) {
    return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
  }
}
