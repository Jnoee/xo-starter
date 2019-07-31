package com.github.jnoee.xo.dfs.fastdfs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.jnoee.xo.dfs.DfsClient;
import com.github.tobato.fastdfs.FdfsClientConfig;

@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@ConditionalOnProperty(name = "xo.dfs.type", havingValue = "fdfs", matchIfMissing = false)
public class FastDfsAutoConfiguration {
  @Bean
  DfsClient fastDfsClient() {
    return new FastDfsClient();
  }
}
