package com.york.javaLearning.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author york
 * @create 2020-06-15 10:59
 **/
public class CglibTest {

    public static void main(String[] args) {
        LogInterceptor logInterceptor = new LogInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDao.class);
        enhancer.setCallback(logInterceptor);
        UserDao userDao = (UserDao) enhancer.create();
        userDao.select();
        userDao.update();
    }
}
