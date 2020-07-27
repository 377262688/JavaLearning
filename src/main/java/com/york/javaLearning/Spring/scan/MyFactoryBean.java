package com.york.javaLearning.Spring.scan;

import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author york
 * @create 2020-07-24 10:20
 **/
public class MyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> myInterface;

    private ConcurrentHashMap<Class<T>,Object> cache = new ConcurrentHashMap<>();
    public MyFactoryBean(Class<T> myInterface) {
        this.myInterface = myInterface;
    }
    @Override
    public T getObject() throws Exception {
        return null;
    }

//    private MapperMethod cachedMapperMethod(Method method) {
//        MapperMethod mapperMethod = methodCache.get(method);
//        if (mapperMethod == null) {
//            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
//            methodCache.put(method, mapperMethod);
//        }
//        return mapperMethod;
//    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
