package com.github.jnoee.xo.web.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.jnoee.xo.exception.BizException;
import com.github.jnoee.xo.message.MessageSource;
import com.github.jnoee.xo.utils.StringUtils;

/**
 * 统一异常处理。
 */
@RestControllerAdvice
public class WebErrorController {
  @Autowired
  private MessageSource messageSource;

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BindException.class)
  public ErrorResponse handleBind(BindException e) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = e.getBindingResult().getAllErrors();
    for (ObjectError bindError : bindErrors) {
      errorMsgs.add(bindError.getDefaultMessage());
    }
    return new ErrorResponse("E990", messageSource.get("E990", StringUtils.join(errorMsgs, "|")));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    List<String> errorMsgs = new ArrayList<>();
    List<ObjectError> bindErrors = e.getBindingResult().getAllErrors();
    for (ObjectError bindError : bindErrors) {
      errorMsgs.add(bindError.getDefaultMessage());
    }
    return new ErrorResponse("E990", messageSource.get("E990", StringUtils.join(errorMsgs, "|")));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ConstraintViolationException.class)
  public ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
    List<String> errorMsgs = new ArrayList<>();
    Set<ConstraintViolation<?>> bindErrors = e.getConstraintViolations();
    for (ConstraintViolation<?> bindError : bindErrors) {
      errorMsgs.add(bindError.getMessage());
    }
    return new ErrorResponse("E990", messageSource.get("E990", StringUtils.join(errorMsgs, "|")));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BizException.class)
  public ErrorResponse handleBiz(BizException e) {
    return new ErrorResponse(e.getCode(), e.getMsg());
  }
}
