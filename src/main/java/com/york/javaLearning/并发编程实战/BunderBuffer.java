package com.york.javaLearning.并发编程实战;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjianzhong
 * @create 2021-04-19 10:26 下午
 **/
public class BunderBuffer {

    private List<Object> list = new ArrayList<>(100);
    public synchronized void put(Object o) throws InterruptedException {
        while (list.size() == 1000) {
            wait();
        }
        list.add(o);
        notifyAll();
    }

    public synchronized Object take() throws InterruptedException {
        while (list.isEmpty()) {
            wait();
        }
        notifyAll();
        return list.get(0);
    }

}
