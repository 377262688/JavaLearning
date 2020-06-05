package com.york.javaLearning.reference;

import sun.misc.Cleaner;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

/**
 * @author york
 * @create 2020-06-05 09:37
 **/
public class ReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        // 强引用
        Integer i = new Integer(1);

        // 弱引用
        SoftReference<Integer> integerSoftReference = new SoftReference<Integer>(new Integer(2));
        ReferenceQueue<Integer> referenceQueue = new ReferenceQueue();
        SoftReference<Integer> softReference = new SoftReference<Integer>(new Integer(2),referenceQueue);

        System.out.println("软引用所指向的对象：" + softReference.get());

        System.gc();

        System.out.println("手动gc之后软引用所指向的对象：" + softReference.get());
        // 构造内存不足
        makeHeapNotEnough();

        System.out.println("通过堆内存不足触发GC:" + softReference.get());
        System.out.println("通过堆内存不足触发GC:" + referenceQueue.poll());

        WeakHashMap<Integer,String> weakHashMap = new WeakHashMap<Integer, String>();
        Integer integer = new Integer(1);
        weakHashMap.put(integer,"111");
        System.out.println("有强引用的时候：map大小" + weakHashMap.size());
        // 去掉强引用
        integer = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println("没有强引用的时候：map大小" + weakHashMap.size());


        ReferenceQueue referenceQueue1 = new ReferenceQueue();
        PhantomReference phantomReference = new PhantomReference(new Integer(24), referenceQueue1);
        System.out.println("什么也不做，获取:" + phantomReference.get());

        Cleaner.create(new Integer(24), new Runnable() {
            public void run() {
                System.out.println("我被回收了，当前线程:{}"+ Thread.currentThread().getName());
            }
        });
        System.gc();
        Thread.sleep(1000);
    }

    /**
     * -Xmx10m 设置堆为10M
     */
    private static void makeHeapNotEnough() {
        SoftReference softReference = new SoftReference(new byte[1024*1024*5]);
        byte[] bytes = new byte[1024*1024*5];
    }
}
