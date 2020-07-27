package com.york.javaLearning.springBeanNotInPackageTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Service;

/**
 * @author york
 * @create 2020-07-02 11:44
 **/
@Service()
public class TestBean1 implements BeanPostProcessor, ApplicationContextAware, ApplicationListener<ApplicationContextEvent> {

    public TestBean1() {
        System.out.println("testBean1注册");
    }

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext");
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        return bean;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent contextEvent) {
        System.out.println("onApplicationEvent");
    }
}
