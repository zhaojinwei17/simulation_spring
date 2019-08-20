package com.zjw.spring.scanner;

import com.zjw.spring.annotation.Component;
import com.zjw.spring.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationScanner {

    //按照注解name存储bean（key为Component的name值）
    Map<String, Object> nameContainer = new HashMap<String, Object>();
    //按照类型存储bean（key为全类名）
    Map<String, Object> typeContainer = new HashMap<String, Object>();
    //存储属性（key为字段，值为对象）
    Map<Field, Object> fieldContainer=new HashMap<Field, Object>();


    public AnnotationScanner(String packageName) {
        Reflections reflections = new Reflections(packageName);
        //获取packageName包下Component所注解的类
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        for (Class clazz :
                typesAnnotatedWith) {
            try {
                //创建bean
                Object bean = clazz.newInstance();
                Component component = (Component) clazz.getAnnotation(Component.class);
                //将bean存入nameContainer容器，获取Component注解的name值，不为空则作为nameContainer，为空则去类的SimpleName作为nameContainer的key
                if (StringUtils.isNotBlank(component.value())) {
                    nameContainer.put(component.value(), bean);
                } else {
                    nameContainer.put(StringUtils.uncapitalize(clazz.getSimpleName()), bean);
                }
                //将bean存入typeContainer容器，key为全类名
                typeContainer.put(clazz.getName(), bean);
                //获取所有属性，包括私有
                Field[] fields = clazz.getDeclaredFields();
                for (Field field :
                        fields) {
                    Resource annotation = field.getAnnotation(Resource.class);
                    //判断该属性是否被Resource注解，将所有被Resource注解的属性存入fieldContainer
                    if(annotation!=null){
                        field.setAccessible(true);
                        fieldContainer.put(field,bean);
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Set<Map.Entry<Field, Object>> entries = fieldContainer.entrySet();
        for (Map.Entry<Field, Object> entry:
             entries) {
            Field field = entry.getKey();
            Object bean = entry.getValue();
            try {
                Resource annotation = field.getAnnotation(Resource.class);
                String name = field.getType().getName();
                Object fieldBean=null;
                if (StringUtils.isNotBlank(annotation.value())){
                    name=annotation.value();
                    fieldBean = nameContainer.get(name);
                }else {
                    fieldBean= typeContainer.get(name);
                }
                if(fieldBean!=null){
                    field.set(bean,fieldBean);
                }else {
                    throw new RuntimeException(" "+name+" bean找不到异常");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public <T> T getBean(String beanName){
        return (T) nameContainer.get(beanName);
    }
    public <T> T getBean(Class clazz){
        String name = clazz.getName();
        return (T) typeContainer.get(name);
    }

}