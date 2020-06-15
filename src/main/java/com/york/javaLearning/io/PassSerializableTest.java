package com.york.javaLearning.io;

import java.io.*;

/**
 * @author york
 * @create 2020-06-15 22:13
 **/
public class PassSerializableTest implements Serializable {
    private static final long serialVersionUID = -4575565609297055604L;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PassSerializableTest passSerializableTest = new PassSerializableTest();
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("PassSerializableTest.obj"));
        outputStream.writeObject(passSerializableTest);
        outputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("PassSerializableTest.obj"));
        passSerializableTest = (PassSerializableTest) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(passSerializableTest.getPass());

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("PassSerializableTest1.obj"));
        objectOutputStream.writeObject(passSerializableTest);
        objectOutputStream.flush();
        System.out.println(new File("PassSerializableTest1.obj").length());
        objectOutputStream.writeObject(passSerializableTest);
        objectOutputStream.flush();
        System.out.println(new File("PassSerializableTest1.obj").length());

    }

    private void writeObject(ObjectOutputStream outputStream) {
        try {
            ObjectOutputStream.PutField putField = outputStream.putFields();
            System.out.println("原密码" +  pass);
            // 模拟加密
            pass = "encryption";
            putField.put("pass",pass);
            System.out.println("加密后的密码" + pass);
            outputStream.writeFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        try {
            ObjectInputStream.GetField getField = objectInputStream.readFields();
            Object o = getField.get("pass","");
            System.out.println("要解密的字符串" + o.toString());
            pass = "pass";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private String pass = "pass";

    public String getPass() {
        return pass;
    }
    public void setPass(String password) {
        this.pass = password;
    }
}
