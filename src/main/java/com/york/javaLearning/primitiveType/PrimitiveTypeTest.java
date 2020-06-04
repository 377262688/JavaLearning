package com.york.javaLearning.primitiveType;

/**
 * @author york
 * @create 2020-06-04 12:03
 **/
public class PrimitiveTypeTest {

    public static void main(String[] args) {
        Integer i = 2;
        Integer j = 2;
        System.out.println(j == i);

        i = 128;
        j = 128;
        System.out.println(j == i);

        i = new Integer(2);
        j = new Integer(2);
        System.out.println(i == j);
        // Integer类重写了equals方法，比较值是否相等
        System.out.println(i.equals(j));
    }
}
