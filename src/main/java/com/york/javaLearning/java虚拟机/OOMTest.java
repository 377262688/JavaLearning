package com.york.javaLearning.java虚拟机;

/**
 * @author york
 * @create 2020-06-08 09:25
 **/
public class OOMTest {

    private static final int _1M = 1024 * 1024;
    public static void main(String[] args) {
        // 11M -Xmx10m -XX:+PrintGC
            byte[] bytes = new byte[_1M * 11];

    }


}
