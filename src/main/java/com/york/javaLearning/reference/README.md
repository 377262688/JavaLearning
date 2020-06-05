# JAVA 引用

## 引用的定义
 ```java
User zhangsan = new User("zhangsan",22);
```
其中 zhangsan 是引用，存储在栈上，指向堆的对象。
![avatar](https://camo.githubusercontent.com/4a8b00de5a588db54b35d99d12dc9474bdd96c5d/68747470733a2f2f757365722d676f6c642d63646e2e786974752e696f2f323031392f322f32382f313639333436396565393138643736663f773d38393226683d35303626663d706e6726733d3238363434)

## 引用的分类

### 强引用
 User zhangsan = new User("zhangsan",22) 中的zhangsan就是强引用。特点如下：
 
 - 强引用可以直接访问目标对象。
 - 只要这个对象被引用指向，并且在 root 节点上，那么垃圾回收器都不会回收，就算是抛出OOM异常。
 - 容易导致内存泄露
 
### 软引用
软引用的构造方法有两个
```java
public SoftReference(T referent);
public SoftReference(T referent, ReferenceQueue<? super T> q);
```

第二个比第一个多了一个引用队列

- 软引用的作用
当程序要出现OOM的时候，被软引用指向的对象将被回收，如果回收了软引用的对象之后内存依然不够，此时会抛出OOM，
软引用如果初始化的时候设置了引用队列，那么这个对象的引用还会进一次引用队列，但是该引用所指向的对象已经被回收。

- 软引用经常用来实现内存敏感的高速缓存
我们知道软引用他只会在内存不足的时候才触发，不会像强引用那用容易内存溢出，我们可以用其实现高速缓存，
一方面内存不足的时候可以回收，一方面也不会频繁回收。在高速本地缓存Caffeine中实现了软引用的缓存，当需要缓存淘汰的时候，
如果是只有软引用指向那么久会被回收。不熟悉Caffeine的同学可以阅读深入理解Caffeine
### 弱引用
弱引用在Java中使用WeakReference来定义一个弱引用，上面我们说过他比软引用更加弱，只要发生垃圾回收，若这个对象只被弱引用指向，那么就会被回收。

- 例如 WeakHashMap
### 虚引用

虚引用是最弱的引用，在Java中使用PhantomReference进行定义。弱到什么地步呢？
也就是你定义了虚引用根本无法通过虚引用获取到这个对象，更别谈影响这个对象的生命周期了。
在虚引用中唯一的作用就是用队列接收对象即将死亡的通知。

虚引用得最多的就是在对象死前所做的清理操作，这是一个比Java的finalization梗灵活的机制。 
在DirectByteBuffer中使用Cleaner用来回收对外内存，Cleaner是PhantomReference的子类，
当DirectByteBuffer被回收的时候未防止内存泄漏所以通过这种方式进行回收。


 