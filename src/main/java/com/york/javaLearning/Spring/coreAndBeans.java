package com.york.javaLearning.Spring;

import com.york.javaLearning.springBeanNotInPackageTest.TestBean1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author york
 * @create 2020-07-02 11:29
 **/
@Configuration
public class coreAndBeans {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.york.javaLearning");
        System.out.println("启动完成");
        System.out.println(applicationContext.getBean(TestBean.class));
        System.out.println(applicationContext.getBean(TestBean1.class));
    }

    @Bean
    public TestBean registerTestBean() {
        return new TestBean();
    }

    public static class TestBean {
        public TestBean() {
            System.out.println("testBeanzhu从");
        }
    }
}
