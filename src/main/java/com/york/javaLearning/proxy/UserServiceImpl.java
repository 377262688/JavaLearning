package com.york.javaLearning.proxy;

/**
 * @author york
 * @create 2020-06-15 09:44
 **/
public class UserServiceImpl implements UserService {
    @Override
    public void select() {
        System.out.println("select method run ....");
    }

    @Override
    public void update() {
        System.out.println("update method run ....");
    }
}
