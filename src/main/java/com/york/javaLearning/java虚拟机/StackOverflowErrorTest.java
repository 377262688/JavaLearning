package com.york.javaLearning.java虚拟机;

/**
 * @author york
 * @create 2020-06-08 09:25
 **/
public class StackOverflowErrorTest {

    public static void main(String[] args) {
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
