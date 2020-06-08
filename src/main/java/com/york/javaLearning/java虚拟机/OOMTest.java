package com.york.javaLearning.java虚拟机;

/**
 * @author york
 * @create 2020-06-08 09:25
 **/
public class OOMTest {

    public static void main(String[] args) {
        // 11M -Xmx10m
//        byte[] bytes = new byte[1024 * 1024 * 11];

        recursivePrint(1);
    }

    public static void recursivePrint(int num) {
        System.out.println("Number: " + num);

        if(num == 0)
            return;
        else
            recursivePrint(++num);
    }
}
