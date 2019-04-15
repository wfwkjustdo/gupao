package com.wufeng.spring.springframework.beans.config;

import lombok.Data;

/**
 * @Auther: ninghu
 * @Description: 存储bean的配置信息，伪 IOC 容器
 */
@Data
public class MyBeanDefinition {

    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
}
