package com.york.javaLearning.proxy;

/**
 * @author york
 * @create 2020-06-14 22:11
 **/
public class ProxyTest {

    public static void main(String[] args) {
        // 静态代理
        UserService target = new UserServiceImpl();
        UserService proxy = new UserServiceProxy(target);
        proxy.select();
        proxy.update();

    }
}
