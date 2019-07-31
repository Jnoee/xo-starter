package com.github.jnoee.xo.dfs.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.jnoee.xo.dfs.fastdfs.FastDfsAutoConfiguration;
import com.github.jnoee.xo.dfs.local.LocalDfsAutoConfiguration;
import com.github.jnoee.xo.dfs.obs.ObsAutoConfiguration;
import com.github.jnoee.xo.dfs.oss.OssAutoConfiguration;

@Configuration
@EnableConfigurationProperties(DfsProperties.class)
@ImportAutoConfiguration({LocalDfsAutoConfiguration.class, FastDfsAutoConfiguration.class,
    ObsAutoConfiguration.class, OssAutoConfiguration.class})
public class DfsAutoConfiguration {
}
