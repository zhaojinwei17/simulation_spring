package com.zjw.spring.test;

import com.zjw.spring.annotation.Component;
import com.zjw.spring.annotation.Resource;

@Component("zz")
public class TestBean2 {
    private String name="zjw";

    @Resource("tt")
    private TestBean1 testBean1;

    @Override
    public String toString() {
        return "TestBean2{" +
                "name='" + name + '\'' +
                '}';
    }
}
