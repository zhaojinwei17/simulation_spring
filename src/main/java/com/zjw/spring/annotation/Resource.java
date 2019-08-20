package com.zjw.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)                  //声明该注解的运行目标:成员
@Retention(value = RetentionPolicy.RUNTIME) //该注解的生命周期: 运行时
public @interface Resource {
    String value() default "";
}
