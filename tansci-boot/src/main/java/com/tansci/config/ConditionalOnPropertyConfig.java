package com.tansci.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName： ConditionalOnPropertyConfig.java
 * @ClassPath： com.tansci.config.ConditionalOnPropertyConfig.java
 * @Description： 条件配置
 * @Author： tanyp
 * @Date： 2022/4/14 14:23
 **/
@Configuration
@ConditionalOnProperty(prefix = "flowable", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ConditionalOnPropertyConfig {
}