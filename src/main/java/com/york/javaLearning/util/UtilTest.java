package com.york.javaLearning.util;

/**
 * @author york
 * @create 2020-06-10 22:08
 **/
public class UtilTest {

    public static void main(String[] args) {
        System.out.println(1 << 30);
        String s = "ssss";
        int h;
        int hash = (h = s.hashCode()) ^ (h >>> 16);
        System.out.println(hash);
        System.out.println((16 - 1) & hash);
        int i = 2;
        int j = 2;
        System.out.println(++i > j);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(1 << 30);
    }

}
