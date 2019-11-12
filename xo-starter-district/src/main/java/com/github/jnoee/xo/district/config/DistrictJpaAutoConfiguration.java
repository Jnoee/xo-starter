package com.github.jnoee.xo.district.config;

import org.hibernate.usertype.UserType;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(UserType.class)
@AutoConfigureAfter(value = JpaRepositoriesAutoConfiguration.class)
@EntityScan("com.github.jnoee.xo.district.jpa")
public class DistrictJpaAutoConfiguration {

}
