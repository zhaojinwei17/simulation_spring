package com.zjw.spring.test;

import com.zjw.spring.annotation.Component;
import com.zjw.spring.annotation.Resource;

@Component("tt")
public class TestBean1 {

    @Resource("zz")
    private TestBean2 testBean2;

    public TestBean2 getTestBean2() {
        return testBean2;
    }
}
