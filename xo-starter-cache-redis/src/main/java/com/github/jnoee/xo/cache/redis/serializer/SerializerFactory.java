package com.github.jnoee.xo.cache.redis.serializer;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.github.jnoee.xo.cache.CacheSerializer;
import com.github.jnoee.xo.exception.SysException;

public class SerializerFactory {
  public static RedisSerializer<Object> create(CacheSerializer type) {
    switch (type) {
      case JAVA:
        return java();
      case JSON:
        return json();
      case FST:
        return fst();
      default:
        throw new SysException("没有匹配的序列化器。");
    }
  }

  public static RedisSerializer<Object> java() {
    return RedisSerializer.java(getClassLoader());
  }

  public static RedisSerializer<Object> json() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
    return new GenericJackson2JsonRedisSerializer(mapper);
  }

  public static RedisSerializer<Object> fst() {
    return FstSerializer.java(getClassLoader());
  }

  private static ClassLoader getClassLoader() {
    return SerializerFactory.class.getClassLoader();
  }

  private SerializerFactory() {}
}
