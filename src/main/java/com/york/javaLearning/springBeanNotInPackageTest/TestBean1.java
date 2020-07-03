package com.york.javaLearning.springBeanNotInPackageTest;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author york
 * @create 2020-07-02 11:44
 **/
@Lazy
@Service()
public class TestBean1 {

    public TestBean1() {
        System.out.println("testBean1注册");
    }
}
