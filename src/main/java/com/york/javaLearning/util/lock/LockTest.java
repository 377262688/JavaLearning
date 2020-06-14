package com.york.javaLearning.util.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author york
 * @create 2020-06-12 16:09
 **/
public class LockTest {

    public static void main(String[] args) {
        System.out.println(null == null);
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    }
}
