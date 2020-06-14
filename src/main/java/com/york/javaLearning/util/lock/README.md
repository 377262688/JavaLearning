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

### AQS 远离概览
AQS核心思想是，如果被请求的共享资源空闲，那么就将当前请求资源的线程设置为有效的工作线程，将共享资源设置为锁定状态；如果共享资源被占用，就需要一定的阻塞等待唤醒机制来保证锁分配。这个机制主要用的是CLH队列的变体实现的，将暂时获取不到锁的线程加入到队列中。

CLH：Craig、Landin and Hagersten队列，是单向链表，AQS中的队列是CLH变体的虚拟双向队列（FIFO），AQS是通过将每条请求共享资源的线程封装成一个节点来实现锁的分配。

主要原理图如下：
![avatar](https://p0.meituan.net/travelcube/7132e4cef44c26f62835b197b239147b18062.png)

### AQS 数据结构
- Node 作为队列的节点数据结构
 
waitStatus ： 当前节点在队列中的状态
thread  ： 该节点所对应的线程
prev ： 前驱节点
next ：后继节点
nextWaiter ： 指向下一个处于CONDITION状态的节点

- 线程的两种锁的模式
 SHARED ： 共享模式
 EXCLUSIVE ： 独占模式
- waitStatus 的状态

0：当一个Node被初始化的默认值
1：线程被取消Node的值
-1：表示线程已经准备好了，就等资源释放了
-2：表示节点在等待队列中，节点线程等待唤醒
-3：当前线程处在SHARED情况下，该字段才会使用

### 同步状态 state
 展示当前AQS获取临界资源的状态，由 volatil 修饰
 
![avatar](https://p0.meituan.net/travelcube/27605d483e8935da683a93be015713f331378.png)
![avatar](https://p0.meituan.net/travelcube/3f1e1a44f5b7d77000ba4f9476189b2e32806.png)

## ReentrantLock 实现

ReentrantLock 实现了两种锁类型，公平锁和非公平锁，均由其中的Sync子类实现，默认为非公平锁。
```java
public ReentrantLock() {
        sync = new NonfairSync();
}
public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
}
```
看看lock方法：
```java
public void lock() {
        sync.lock();
}
```
其中 sync 由构造方法初始化。
看看 sync.lock() 方法实现的区别
```java
// FairSync
final void lock() {
    acquire(1);
}
// NonfairSync
final void lock() {
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
```
可以看出，他们之间的区别是申请临界区资源时，公平锁会直接进入队列，而非公平锁会直接申请资源，当没申请到时再进入队列。
，直接申请资源的代码也很简单，都是调用AQS的方法，CAS改变state的值，如果改变成功，说明申请到资源，设置当前线程为持有资源的线程。
由此来实现非公平锁。

再看看 acquire 方法的实现：acquire 方法是AQS 实现：
```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```
其中 AQS 中tryAcquire方法直接抛出UnsupportedOperationException，说明方法需要子类实现。
tryAcquire方法的作用是尝试申请资源，对比FairSync 和 NonfairSync 的实现代码：
```java
// FairSync
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    } else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
// NonfairSync
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) { 
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```
其中的代码都很简单，都是判断当前的state是否为0，如果为0表示当前没有线程占用，直接申请资源，其中的区别在于
FairSync中的CAS申请资源之前，先执行了!hasQueuedPredecessors() 方法判断。如果不为0，就判断当前线程是否是
持有该资源的线程，如果是，就该该state的数量加上当前申请的资源数。由此来实现重入。
那么 hasQueuedPredecessors() 方法是干什么的呢？
hasQueuedPredecessors是公平锁加锁时判断等待队列中是否存在有效节点的方法。
如果返回False，说明当前线程可以争取共享资源；如果返回True，说明队列中存在有效节点，
当前线程必须加入到等待队列中。由此方法来实现是否公平。

如果tryAcquire获取资源失败，则执行入队操作 acquireQueued(addWaiter(Node.EXCLUSIVE), arg)
，首先执行addWaiter(Node.EXCLUSIVE)，该方法实现如下：
```java
private Node addWaiter(Node mode) {
    // 新建一个mode模式的节点，mode的选择有独占和共享两种选择，是Node中的静态变量
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    // 获取尾节点，如果尾节点不为空，将当前新建节点的前驱节点设为该尾节点，
    // CAS更改tail的指针，如果修改成功，入队成功，修改之前尾节点的后继节点为该节点
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    // 否则执行入队操作
    enq(node);
    return node;
}

private Node enq(final Node node) {
// 获取尾节点，如果为空，说明该链表还未初始化，CAS初始化，否则执行加入尾节点
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}
```

addWaiter方法返回就是把当前线程以Node的形式入队，并且返回这个节点。
该节点与上面传入的args参数一起作为acquireQueued方法的入参。acquireQueued方法可以对排队中的线程进行“获锁”操作。

总的来说，一个线程获取锁失败了，被放入等待队列，acquireQueued会把放入队列中的线程不断去获取锁，直到获取成功或者不再需要获取（中断）。

下面我们从“何时出队列？”和“如何出队列？”两个方向来分析一下acquireQueued源码：
```java
final boolean acquireQueued(final Node node, int arg) {
    // 标记是否成功拿到资源
    boolean failed = true;
    try {
        // 标记等待过程中是否被终端
        boolean interrupted = false;
        for (;;) {
            // 获取当前节点的前驱节点
            final Node p = node.predecessor();
            // 如果前驱节点为head，说明当前节点为第一个节点(head节点为虚节点，不存放资源),尝试获取锁
            if (p == head && tryAcquire(arg)) {
                // 获取到锁，设置该节点为头节点，并且将该节点的thread 和 prev都设置为null(虚节点)，但是并没有修改waitStatus
                setHead(node);
                // 将以前的前驱节点的下一个节点设置为null，help GC
                p.next = null; // help GC
                // 标记成功拿到资源
                failed = false;
                // 返回未被中断
                return interrupted;
            }
            // 说明p为头节点且当前没有获取到锁（可能是非公平锁被抢占了）或者是p不为头结点，
            // 这个时候就要判断当前node是否要被阻塞（被阻塞条件：前驱节点的waitStatus为-1）
            // ，防止无限循环浪费资源。具体两个方法下面细细分析
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}


private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    // 获取头节点的状态
    int ws = pred.waitStatus;
    // 如果头节点状态为-1，说明，头节点是等待唤醒状态，当前线程应该被阻塞
    if (ws == Node.SIGNAL)
        return true;
    // 如果头节点的状态大于0，说明是取消状态，向前遍历链表，将该节点的前驱节点指向小于1的节点,把取消节点剔除
    if (ws > 0) {
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        // 否则，设置前任节点的状态为SIGNAL
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}
// 该方法主要用于挂起当前线程，并且在唤醒时，返回该中断状态
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}

```
流程图为：
![avtar](https://p0.meituan.net/travelcube/c124b76dcbefb9bdc778458064703d1135485.png)
从上图可以看出，跳出当前循环的条件是当“前置节点是头结点，且当前线程获取锁成功”。
为了防止因死循环导致CPU资源被浪费，我们会判断前置节点的状态来决定是否要将当前线程挂起，
具体挂起流程用流程图表示如下（shouldParkAfterFailedAcquire流程）：
![avtar](https://p0.meituan.net/travelcube/9af16e2481ad85f38ca322a225ae737535740.png)

从队列中释放节点的疑虑打消了，那么又有新问题了：

- shouldParkAfterFailedAcquire中取消节点是怎么生成的呢？什么时候会把一个节点的waitStatus设置为-1？

- 是在什么时间释放节点通知到被挂起的线程呢？

### CANCELLED状态节点生成

acquireQueued方法中有个finally块，块中代码为：
```java
if (failed)
    cancelAcquire(node);
}
```
如果获取到锁，failed被标记为false，不会执行，其他情况都会执行，

```java
private void cancelAcquire(Node node) {
  // 将无效节点过滤
	if (node == null)
		return;
  // 设置该节点不关联任何线程，也就是虚节点
	node.thread = null;
	Node pred = node.prev;
  // 通过前驱节点，跳过取消状态的node
	while (pred.waitStatus > 0)
		node.prev = pred = pred.prev;
  // 获取过滤后的前驱节点的后继节点
	Node predNext = pred.next;
  // 把当前node的状态设置为CANCELLED
	node.waitStatus = Node.CANCELLED;
  // 如果当前节点是尾节点，将从后往前的第一个非取消状态的节点设置为尾节点
  // 更新失败的话，则进入else，如果更新成功，将tail的后继节点设置为null
	if (node == tail && compareAndSetTail(node, pred)) {
		compareAndSetNext(pred, predNext, null);
	} else {
		int ws;
    // 如果当前节点不是head的后继节点，1:判断当前节点前驱节点的是否为SIGNAL，2:如果不是，则把前驱节点设置为SINGAL看是否成功
    // 如果1和2中有一个为true，再判断当前节点的线程是否为null
    // 如果上述条件都满足，把当前节点的前驱节点的后继指针指向当前节点的后继节点
		if (pred != head && ((ws = pred.waitStatus) == Node.SIGNAL || (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) && pred.thread != null) {
			Node next = node.next;
			if (next != null && next.waitStatus <= 0)
				compareAndSetNext(pred, predNext, next);
		} else {
      // 如果当前节点是head的后继节点，或者上述条件不满足，那就唤醒当前节点的后继节点
			unparkSuccessor(node);
		}
		node.next = node; // help GC
	}
}
```

## 如何解锁

直接在sync中定义了release操作
```java
// java.util.concurrent.locks.AbstractQueuedSynchronizer

public final boolean release(int arg) {
    // 如果tryRelease 返回为true说明该锁没有被线程持有
	if (tryRelease(arg)) {
	    // 找到头节点
		Node h = head;
		// 如果头节点不为空，并且头节点的状态不是初始化的状态，接触挂起状态
		if (h != null && h.waitStatus != 0)
			unparkSuccessor(h);
		return true;
	}
	return false;
}

// java.util.concurrent.locks.ReentrantLock.Sync

// 方法返回当前锁是不是没有被线程持有
protected final boolean tryRelease(int releases) {
	// 减少可重入次数
	int c = getState() - releases;
	// 当前线程不是持有锁的线程，抛出异常
	if (Thread.currentThread() != getExclusiveOwnerThread())
		throw new IllegalMonitorStateException();
	boolean free = false;
	// 如果持有线程全部释放，将当前独占锁所有线程设置为null，并更新state
	if (c == 0) {
		free = true;
		setExclusiveOwnerThread(null);
	}
	setState(c);
	return free;
}
```
这里的判断条件为什么是h != null && h.waitStatus != 0？
```java
h == null Head还没初始化。初始情况下，head == null，第一个节点入队，Head会被初始化一个虚拟节点。所以说，这里如果还没来得及入队，就会出现head == null 的情况。

h != null && waitStatus == 0 表明后继节点对应的线程仍在运行中，不需要唤醒。

h != null && waitStatus < 0 表明后继节点可能被阻塞了，需要唤醒。
```

```java
private void unparkSuccessor(Node node) {
	// 获取头结点waitStatus
	int ws = node.waitStatus;
	if (ws < 0)
		compareAndSetWaitStatus(node, ws, 0);
	// 获取当前节点的下一个节点
	Node s = node.next;
	// 如果下个节点是null或者下个节点被cancelled，就找到队列最开始的非cancelled的节点
	if (s == null || s.waitStatus > 0) {
		s = null;
		// 就从尾部节点开始找，到队首，找到队列第一个waitStatus<0的节点。
		for (Node t = tail; t != null && t != node; t = t.prev)
			if (t.waitStatus <= 0)
				s = t;
	}
	// 如果当前节点的下个节点不为空，而且状态<=0，就把当前节点unpark
	if (s != null)
		LockSupport.unpark(s.thread);
}
```
