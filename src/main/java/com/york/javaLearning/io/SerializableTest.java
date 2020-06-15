package com.york.javaLearning.io;

import java.io.*;

/**
 * @author york
 * @create 2020-06-15 21:58
 **/
public class SerializableTest implements Serializable {
    private static final long serialVersionUID = 3923091086326780843L;

    public static int staticVar = 5;

    public int var = 1;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("result.obj"));
        SerializableTest old = new SerializableTest();
        outputStream.writeObject(old);
        old.setVar();
        staticVar = 10;
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("result.obj"));
        old = (SerializableTest) objectInputStream.readObject();
        objectInputStream.close();
        // 序列化不保存静态变量，因为静态变量属于类，不属于对象
        //再读取，通过t.staticVar打印新的值
        System.out.println(SerializableTest.staticVar);
        System.out.println(old.var);
    }

    public void setVar() {
        this.var = 20;
    }
}
