package com.wufeng.spring.springframework.core;

/**
 * @Auther: ninghu
 * @Description:
 */
public interface MyBeanFactory {

    /**
     * @Description: 根据beanName从 IOC 容器从获得一个实例
     *
     * @author ninghu
     * @since 2019-04-14
     */
    Object getBean(String beanName) throws Exception;
}
