package com.york.javaLearning.util;

import java.util.concurrent.TimeUnit;

/**
 * @author york
 * @create 2020-06-17 10:58
 **/
public class VolatileTest implements Runnable {

    private static volatile boolean flag = true;
    @Override
    public void run() {
        while (flag) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在运行");
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕。。。。");
    }
    private void stopThread() {
        flag = false;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        new Thread(volatileTest,"threadA").start();
        System.out.println("main线程正在运行");
        TimeUnit.MILLISECONDS.sleep(100);
        volatileTest.stopThread();
    }

}
