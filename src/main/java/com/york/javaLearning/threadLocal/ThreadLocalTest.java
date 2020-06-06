package com.york.javaLearning.threadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author york
 * @create 2020-06-05 11:07
 **/
public class ThreadLocalTest extends Thread {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal();

    private static final Random random = new Random();

    @Override
    public void run() {
        int localValue = random.nextInt();
        threadLocal.set(localValue);
        System.out.println("Thread: " + Thread.currentThread().getName() + " set thread local: " + localValue);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread: " + Thread.currentThread().getName() + " threadLocal: " + threadLocal.get() + ", local: " + localValue);
    }

    public static void main(String[] args) {
        int concurrent = 10;
        ExecutorService service = Executors.newFixedThreadPool(concurrent);
        for (int i = 0; i < concurrent; i++) {
            service.execute(new ThreadLocalTest());
        }
        service.shutdown();

    }
}
