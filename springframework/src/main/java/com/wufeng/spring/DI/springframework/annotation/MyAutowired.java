package com.wufeng.spring.DI.springframework.annotation;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
	String value() default "";
}
