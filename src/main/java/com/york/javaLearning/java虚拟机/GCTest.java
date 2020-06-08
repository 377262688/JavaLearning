package com.york.javaLearning.java虚拟机;

/**
 * @author york
 * @create 2020-06-08 09:58
 **/
public class GCTest {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        // 11M -Xmx10m -XX:+PrintGC
        for (int i = 0; i < 22; i++) {
            byte[] bytes = new byte[_1M];
        }
    }
}
