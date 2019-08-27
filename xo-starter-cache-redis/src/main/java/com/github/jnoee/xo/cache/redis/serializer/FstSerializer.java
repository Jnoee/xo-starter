package com.github.jnoee.xo.cache.redis.serializer;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;

public class FstSerializer implements RedisSerializer<Object> {
  private FSTConfiguration conf;

  public static FstSerializer java() {
    FstSerializer serializer = new FstSerializer();
    serializer.conf = FSTConfiguration.createDefaultConfiguration();
    return serializer;
  }

  public static FstSerializer java(ClassLoader classLoader) {
    FstSerializer serializer = java();
    serializer.conf.setClassLoader(classLoader);
    return serializer;
  }

  public static FstSerializer json() {
    FstSerializer serializer = new FstSerializer();
    serializer.conf = FSTConfiguration.createJsonConfiguration();
    return serializer;
  }

  public static FstSerializer json(ClassLoader classLoader) {
    FstSerializer serializer = json();
    serializer.conf.setClassLoader(classLoader);
    return serializer;
  }

  @Override
  public byte[] serialize(Object t) {
    return conf.asByteArray(t);
  }

  @Override
  public Object deserialize(byte[] bytes) {
    return conf.asObject(bytes);
  }
}
