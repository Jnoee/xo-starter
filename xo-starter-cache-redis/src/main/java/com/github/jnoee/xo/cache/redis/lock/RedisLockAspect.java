package com.github.jnoee.xo.cache.redis.lock;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jnoee.xo.exception.SysException;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class RedisLockAspect {
  @Autowired
  private RedisLocker redisLocker;

  @Around(value = "@annotation(redisLock)")
  public Object doAround(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
    String uuid = UUID.randomUUID().toString();
    Boolean locked = redisLocker.lock(redisLock.key(), uuid, redisLock.timeout());
    log.debug("获取分布式锁[{}]：{}", redisLock.key(), locked);
    if (!locked) {
      throw new SysException("获取分布式锁[" + redisLock.key() + "]失败。");
    }
    try {
      return joinPoint.proceed();
    } finally {
      Boolean unlocked = redisLocker.unlock(redisLock.key(), uuid);
      log.debug("释放分布式锁[{}]：{}", redisLock.key(), unlocked);
    }
  }
}
