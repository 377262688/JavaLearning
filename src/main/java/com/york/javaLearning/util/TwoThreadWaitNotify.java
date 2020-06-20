package com.york.javaLearning.util;

/**
 * @author york
 * @create 2020-06-17 09:19
 **/
public class TwoThreadWaitNotify {

    private static Object object = new Object();

    private static boolean flag = true;

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new OuThread());
        thread.setName("偶线程");
        Thread thread1 = new Thread(new QiThread());
        thread1.setName("奇线程");

        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
    }

    public static class OuThread implements Runnable {

        @Override
        public void run() {
           while (i < 100) {
               synchronized (object) {
                   if (flag) {
                       System.out.println(Thread.currentThread().getName() + "---" + i);
                       i++;
                       flag = !flag;
                       object.notify();
                   } else {
                       try {
                           object.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
           }
        }
    }

    public static class QiThread implements Runnable {

        @Override
        public void run() {
            while (i < 100) {
                synchronized (object) {
                    if (!flag) {
                        System.out.println(Thread.currentThread().getName() + "---" + i);
                        i++;
                        flag = !flag;
                        object.notify();
                    } else {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
