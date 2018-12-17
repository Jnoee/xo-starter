package com.github.jnoee.xo.ienum.config;

import org.hibernate.usertype.UserType;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * 枚举增强JPA配置。
 */
@Configuration
@ConditionalOnClass(UserType.class)
@AutoConfigureAfter(value = JpaRepositoriesAutoConfiguration.class)
@EntityScan("com.github.jnoee.xo.ienum.usertype")
public class IEnumJpaAutoConfiguration {

}
