package com.york.javaLearning.primitiveType;

/**
 * 打印int数据二进制
 * @author york
 * @create 2020-12-21 17:37
 **/
public class BinaryOut {

    public static void main(String[] args) {
        int i = 0x80000000;
        for (int j = 0; j < 32; j++) {
            int t = (i & 0x80000000>>>j) >>> (31 - j);
            System.out.print(t);
        }
    }
}
