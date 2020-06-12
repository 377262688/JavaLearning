# AbstractQueuedSynchronizer

Java中的大部分同步类（Lock、Semaphore、ReentrantLock等）都是基于AbstractQueuedSynchronizer（简称为AQS）实现的
，参考美团技术团队https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html

AQS是一种提供了原子式管理同步状态、阻塞和唤醒线程功能以及队列模型的简单框架。

## 1 ReentrantLock 和 synchronized 比较

两者都为可重入锁

![avatar](https://p0.meituan.net/travelcube/412d294ff5535bbcddc0d979b2a339e6102264.png)

```java
// **************************Synchronized的使用方式**************************
// 1.用于代码块
synchronized (this) {}
// 2.用于对象
synchronized (object) {}
// 3.用于方法
public synchronized void test () {}
// 4.可重入
for (int i = 0; i < 100; i++) {
	synchronized (this) {}
}
// **************************ReentrantLock的使用方式**************************
public void test () throw Exception {
	// 1.初始化选择公平锁、非公平锁
	ReentrantLock lock = new ReentrantLock(true);
	// 2.可用于代码块
	lock.lock();
	try {
		try {
			// 3.支持多种加锁方式，比较灵活; 具有可重入特性
			if(lock.tryLock(100, TimeUnit.MILLISECONDS)){ }
		} finally {
			// 4.手动释放锁
			lock.unlock()
		}
	} finally {
		lock.unlock();
	}
}
```
## 2 AQS
![avatar](https://p1.meituan.net/travelcube/82077ccf14127a87b77cefd1ccf562d3253591.png)

当有自定义同步器接入时，只需要重写API层方法即可。
