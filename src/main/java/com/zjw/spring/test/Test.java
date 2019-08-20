package com.zjw.spring.test;

import com.zjw.spring.scanner.AnnotationScanner;

public class Test {

    public static void main(String[] args) {
        AnnotationScanner annotationScanner = new AnnotationScanner("com.zjw.spring.test");
        TestBean1 testBean1=annotationScanner.getBean(TestBean1.class);
        System.out.println(testBean1.getTestBean2());
    }
}
