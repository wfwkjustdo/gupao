package com.wufeng.spring.DI.springframework.beans.support;

import com.wufeng.spring.DI.springframework.beans.config.MyBeanDefinition;
import com.wufeng.spring.DI.springframework.context.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: ninghu
 * @Description: 默认的 BeanFactory
 */
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext {

    //存储注册信息的 BeanDefinition
    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap(256);
}
