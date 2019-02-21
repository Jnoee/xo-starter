package com.github.jnoee.xo.id;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jnoee.xo.exception.SysException;
import com.github.jnoee.xo.id.config.IdProperties;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;
import lombok.extern.slf4j.Slf4j;

/**
 * 分布式主键生成器工厂。
 */
@Slf4j
public class IdGeneratorFactoryBean implements FactoryBean<KeyGenerator> {
  @Autowired
  private IdProperties idProperties;
  private KeyGenerator generator;

  @PostConstruct
  public void init() {
    Long workerId = null;
    switch (idProperties.getType()) {
      case "host":
        workerId = genWorkerIdByHost();
        break;
      case "ip":
        workerId = genWorkerIdByIp();
        break;
      default:
        throw new SysException("不支持的分布式id生成器类型。");
    }
    DefaultKeyGenerator.setWorkerId(workerId);
    log.info("启用雪花算法分布式主键生成器，workerId生成器类型为[{}]，生成值为[{}]。", idProperties.getType(), workerId);
    generator = new DefaultKeyGenerator();
  }

  @Override
  public KeyGenerator getObject() throws Exception {
    return generator;
  }

  @Override
  public Class<?> getObjectType() {
    return KeyGenerator.class;
  }

  /**
   * 根据主机名生成workerId。
   * 
   * @return 返回workerId。
   */
  private Long genWorkerIdByHost() {
    InetAddress address = getLocalHost();
    String hostName = address.getHostName();
    try {
      return Long.valueOf(hostName.replace(hostName.replaceAll("\\d+$", ""), ""));
    } catch (final NumberFormatException e) {
      throw new SysException("基于主机名生成workerId失败，主机名[" + hostName + "]需以数字结尾。");
    }
  }

  /**
   * 根据主机IP生成workerId。
   * 
   * @return 返回workerId。
   */
  private Long genWorkerIdByIp() {
    InetAddress address = getLocalHost();
    byte[] ipAddressByteArray = address.getAddress();
    long workerId = 0L;
    if (ipAddressByteArray.length == 4) {
      for (byte byteNum : ipAddressByteArray) {
        workerId += byteNum & 0xFF;
      }
    }
    if (ipAddressByteArray.length == 16) {
      for (byte byteNum : ipAddressByteArray) {
        workerId += byteNum & 0B111111;
      }
    }
    return workerId;
  }

  /**
   * 获取主机地址对象。
   * 
   * @return 返回主机地址对象。
   */
  private InetAddress getLocalHost() {
    try {
      return InetAddress.getLocalHost();
    } catch (final UnknownHostException e) {
      throw new SysException("获取主机地址失败。");
    }
  }
}
