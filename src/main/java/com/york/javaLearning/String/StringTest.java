package com.york.javaLearning.String;

/**
 * @author york
 * @create 2020-06-04 12:20
 **/
public class StringTest {

    public static void main(String[] args) {
        String s = "ssss";
        String s1 = "ssss".intern();
        System.out.println(s == s1);

        s = new String("s");
        s1 = new String("s");

        System.out.println(s == s1);

        s = "s1";
        s1 = "s1";

        System.out.println(s == s1);
    }
}
