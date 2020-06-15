package com.york.javaLearning.proxy;

/**
 * @author york
 * @create 2020-06-14 22:15
 **/
public class SonClass extends FatherClass {

    private String mSonName;
    private volatile int mSonAge;
    public static final String mSonBirthday = "11";

    public SonClass() {
        System.out.println("构造方法调用");
    }

    public SonClass(String mSonName) {
        System.out.println("有参构造方法调用");
        this.mSonName = mSonName;
    }

    public void printSonMsg(){
        System.out.println("Son Msg - name : "
                + mSonName + "; age : " + mSonAge);
    }

    private void setSonName(String name){
        mSonName = name;
    }

    private void setSonAge(int age){
        System.out.println("设置对象年龄为" + age);
        mSonAge = age;
    }

    public int getSonAge(){
        return mSonAge;
    }

    private String getSonName(){
        return mSonName;
    }
}
