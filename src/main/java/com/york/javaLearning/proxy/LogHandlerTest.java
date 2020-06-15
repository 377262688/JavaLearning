package com.york.javaLearning.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author york
 * @create 2020-06-15 10:07
 **/
public class LogHandlerTest {

    public static void main(String[] args) {

        // 设置变量可以保存动态代理类，默认名称以 $Proxy0 格式命名
        // System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 1. 创建被代理的对象，UserService接口的实现类
        UserService userService = new UserServiceImpl();

        ClassLoader classLoader = userService.getClass().getClassLoader();

        Class[] interfaces = userService.getClass().getInterfaces();
        // 4. 创建一个将传给代理类的调用请求处理器，处理所有的代理对象上的方法调用
        // 这里创建的是一个自定义的日志处理器，须传入实际的执行对象 userService
        InvocationHandler logHandler = new LogHandler(userService);
        //    5.根据上面提供的信息，创建代理对象 在这个过程中，
        //               a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
        //               b.然后根据相应的字节码转换成对应的class，
        //               c.然后调用newInstance()创建代理实例
        UserService proxy = (UserService) Proxy.newProxyInstance(classLoader,interfaces,logHandler);
        proxy.select();
        proxy.update();

        ProxyUtil.generateClassFile(userService.getClass(),"UserServiceProxy");
    }
}
