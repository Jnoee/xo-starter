package com.github.jnoee.xo.cache.redis.lock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

public class RedisLocker {
  @Autowired
  private StringRedisTemplate redisTemplate;

  /**
   * 加锁。
   * 
   * @param key 键名
   * @param value 键值
   * @param timeout 过期时间
   * @return 返回是否成功加锁。
   */
  public Boolean lock(String key, String value, Integer timeout) {
    return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
  }

  /**
   * 解锁。
   * 
   * @param key 键名
   * @param value 键值
   * @return 返回是否成功解锁。
   */
  public Boolean unlock(String key, String value) {
    Long result = redisTemplate.execute(genUnlockScript(), Arrays.asList(key, value));
    return result == 1L;
  }

  /**
   * 生成解锁脚本。
   * 
   * @return 返回解锁脚本。
   */
  private DefaultRedisScript<Long> genUnlockScript() {
    DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
    defaultRedisScript.setResultType(Long.class);
    defaultRedisScript.setScriptText(
        "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end");
    return defaultRedisScript;
  }
}
