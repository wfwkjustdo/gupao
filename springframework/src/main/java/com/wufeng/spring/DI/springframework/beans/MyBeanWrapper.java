package com.wufeng.spring.DI.springframework.beans;


/**
 * @Auther: ninghu
 * @Description: 保存 bean 实例
 */
public class MyBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public MyBeanWrapper(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
