package com.wufeng.spring.DI.springframework.annotation;

import java.lang.annotation.*;


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {
	
	String value() default "";
	
	boolean required() default true;

}
