package com.york.javaLearning.proxy;

import java.lang.reflect.*;

/**
 * @author york
 * @create 2020-06-14 22:15
 **/
public class ReflectTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException, InstantiationException {
        Class mClass = SonClass.class;
        System.out.println("类的名称：" + mClass.getName());

        // 获取该类所有字段，不包括父类字段
        Field[] fields = mClass.getDeclaredFields();
        // 获取所有public字段，包括父类字段
//        fields = mClass.getFields();
        for (Field field : fields) {
            System.out.println("类的字段：" + field.getName());
            System.out.println("字段的修饰字段：" + Modifier.toString(field.getModifiers()));
            System.out.println("字段的类型" + field.getGenericType().toString());
        }

        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        Method[] methods = mClass.getMethods();
        // 获取本类的所有方法，不问访问权限
        methods = mClass.getDeclaredMethods();
        for (Method method : methods) {
            //获取并输出方法的访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //获取并输出方法的返回值类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName() + " " + method.getName() + "( ");
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getType().getName()
                        + " " + parameter.getName() + ",");
            }
            //获取并输出方法抛出的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0) {
                System.out.println(" )");
            } else {
                for (Class c : exceptionTypes) {
                    System.out.println(" ) throws "
                            + c.getName());
                }
            }
        }


        // 访问私有方法并执行
        Method privateMethod = mClass.getDeclaredMethod("setSonAge", int.class);
        //获取私有方法的访问权
        //只是获取访问权，并不是修改实际权限
        privateMethod.setAccessible(true);
        // 实例对象
        SonClass sonClass = new SonClass();
        // 实际执行方法
        privateMethod.invoke(sonClass, 1);


        // 访问私有变量，并且修改其值
        Field privateFiled = mClass.getDeclaredField("mSonAge");
        privateFiled.setAccessible(true);
        System.out.println("修改变量之前的值" + sonClass.getSonAge());
        privateFiled.set(sonClass,111);
        System.out.println("修改变量之后的值" + sonClass.getSonAge());
        // 类加载
//        Class.forName("");
//        // 指定 classloader 和 是否执行 cinit方法
//        Class.forName("",false,mClass.getClassLoader());
//        // 通过classLoder 加载类
//        mClass.getClassLoader().loadClass("");
        // Class.forName()：将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块；
        //ClassLoader.loadClass()：只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去执行static块。
        //Class.forName(name, initialize, loader)带参函数也可控制是否加载static块。并且只有调用了newInstance()方法采用调用构造函数，创建类的对象。

        sonClass = (SonClass) mClass.newInstance();
        // 获取无参构造方法
        Constructor<SonClass> constructor = mClass.getConstructor(String.class);
        sonClass = constructor.newInstance("ddd");
    }
}
