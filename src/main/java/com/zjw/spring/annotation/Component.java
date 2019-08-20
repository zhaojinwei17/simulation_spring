package com.zjw.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)                   //声明该注解的运行目标:类
@Retention(value = RetentionPolicy.RUNTIME) //该注解的生命周期: 运行时
public @interface Component {
    String value() default "";
    String type() default "";
}
