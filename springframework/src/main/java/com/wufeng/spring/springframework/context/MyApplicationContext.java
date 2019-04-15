package com.wufeng.spring.springframework.context;

import com.wufeng.spring.springframework.annotation.MyAutowired;
import com.wufeng.spring.springframework.annotation.MyController;
import com.wufeng.spring.springframework.annotation.MyService;
import com.wufeng.spring.springframework.beans.MyBeanWrapper;
import com.wufeng.spring.springframework.beans.config.MyBeanDefinition;
import com.wufeng.spring.springframework.beans.config.MyBeanPostProcessor;
import com.wufeng.spring.springframework.beans.support.MyBeanDefinitionReader;
import com.wufeng.spring.springframework.beans.support.MyDefaultListableBeanFactory;
import com.wufeng.spring.springframework.core.MyBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: ninghu
 * @Description: 类似AnnotationConfigApplicationContext、ClassPathXmlApplicationContext等
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {

    private String[] configLocations;
    private MyBeanDefinitionReader reader;
    //单例的IOC容器缓存
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    //通用的IOC容器
    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, MyBeanWrapper>();


    public MyApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: Spring 重启方法
     *
     * @author ninghu
     * @since 2019-04-14
     */
    @Override
    public void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new MyBeanDefinitionReader(this.configLocations);

        //2、加载配置文件，扫 相关的类，把它们封装成 BeanDefinition
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪 IOC 容器)
        doRegisterBeanDefinition(beanDefinitions);
        
        //4、把不是延时加载的类，有 前初始化
        doAutowrited();
    }

    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions) throws Exception {
        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    private void doAutowrited() {
        for (Map.Entry<String, MyBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey(); if(!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    /**
     * @Description:
     * 依赖注入，从这里开始，通过读取 BeanDefinition 中的信息
     * 然后，通过反射机制创建一个实例并返回
     * Spring 做法是，不会把最原始的对象放出去，会用一个 BeanWrapper 来进行一次包装
     * 装饰器模式:
     * 1、保留原来的 OOP 关系
     * 2、我需要对它进行扩展，增强(为了以后 AOP 打基础)
     * @author ninghu
     */
    public Object getBean(String beanName) {
        MyBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        try {
            MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
            //在实例初始化以前调用一次
            instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);

            //实例化方法
            instance = instantiateBean(beanDefinition);

            if(null == instance){ return null;}
            MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);
            this.factoryBeanInstanceCache.put(beanName, beanWrapper);

            //在实例初始化以后调用一次
            beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            populateBean(beanName,instance);
        } catch (Exception e) {
            return null;
        }
        //通过这样一调用，相当于给我们自己留有了可操作的空间
        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[beanDefinitionMap.size()]);
    }

    /**
     * @Description: 传一个 BeanDefinition，就返回一个实例 Bean
     * @author ninghu
     */
    private Object instantiateBean(MyBeanDefinition beanDefinition) {
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try{
            //假设默认就是单例
            if(this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else{
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonObjects.put(beanDefinition.getFactoryBeanName(), instance);
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void populateBean(String beanName, Object instance) {
        Class clazz = instance.getClass();
        //不是所有牛奶都叫特仑苏
        if(!(clazz.isAnnotationPresent(MyController.class)
                || clazz.isAnnotationPresent(MyService.class))){ return; }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(MyAutowired.class)){ continue; }
            MyAutowired autowired = field.getAnnotation(MyAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
