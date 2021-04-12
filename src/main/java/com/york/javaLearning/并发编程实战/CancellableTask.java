package com.york.javaLearning.并发编程实战;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @author york
 * @create 2021-04-12 下午2:28
 **/
public interface CancellableTask<T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}
