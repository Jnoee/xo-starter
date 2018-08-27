package com.github.jnoee.xo.jpa.ctut.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.github.jnoee.xo.jpa.ctut.annotation.Ctut;
import com.github.jnoee.xo.jpa.ctut.entity.CtutEntity;

/**
 * Ctut切面。
 */
@Aspect
public class CtutAspect {
  /**
   * 切面处理方法。
   * 
   * @param joinPoint 切入点
   */
  @Before("@annotation(ctut)")
  public void before(JoinPoint joinPoint, Ctut ctut) {
    Object[] params = joinPoint.getArgs();
    for (Object param : params) {
      if (param instanceof CtutEntity<?>) {
        ((CtutEntity<?>) param).autoFillIn();
      }
    }
  }
}
