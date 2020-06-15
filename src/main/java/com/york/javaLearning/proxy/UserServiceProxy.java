package com.york.javaLearning.proxy;

import java.time.LocalDateTime;

/**
 * @author york
 * @create 2020-06-15 09:45
 **/
public class UserServiceProxy implements UserService {
    private UserService target;
    public UserServiceProxy(UserService target) {
        this.target = target;
    }
    @Override
    public void select() {
        before();
        target.select();
        after();
    }

    @Override
    public void update() {
        before();
        target.update();
        after();
    }

    private void before() {     // 在执行方法之前执行
        System.out.println(String.format("log start time [%s] ",LocalDateTime.now()));
    }
    private void after() {      // 在执行方法之后执行
        System.out.println(String.format("log end time [%s] ", LocalDateTime.now()));
    }
}
