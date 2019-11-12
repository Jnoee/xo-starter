package com.github.jnoee.xo.district.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DistrictJpaAutoConfiguration.class, DistrictWebAutoConfiguration.class})
public class DistrictAutoConfiguration {

}
