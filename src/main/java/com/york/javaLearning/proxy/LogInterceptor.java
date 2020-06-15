package com.york.javaLearning.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author york
 * @create 2020-06-15 10:57
 **/
public class LogInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object o1 = methodProxy.invokeSuper(o,objects);
        after();
        return o1;
    }

    private void before() {
        System.out.println(String.format("log start time [%s] ", LocalDateTime.now()));
    }
    private void after() {
        System.out.println(String.format("log end time [%s] ", LocalDateTime.now()));
    }
}
