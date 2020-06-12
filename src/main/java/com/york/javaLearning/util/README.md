# java.util 包中的工具类

# Map

 ## HashMap
- 哈希表的实现
- 无序
- 访问速度快
- key不允许重复（只允许存在一个null key）
 其中用了 tableSizeFor(int cap) 方法来计算容量，此方法的作用是保证 数组大小总为2的整数幂。参见https://blog.csdn.net/Z_ChenChen/article/details/82955221
 threshold 的值为 cap * loadFactor; 例如 数组容量16 ，负载因子 0.75 ，则 threshold 的值为12
 ### Node<K,V>()
  该 Node 为保存实际数据的结构，其中有成员变量 int hash,K key,V value 和 Node next;可以理解为链表节点。
 ### putVal() :
 该方法签名为
 ```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,boolean evict){}
```
当调用 put(K k,V v) 方法时，直接加上 hash值来调用putVal() 方法。
- 1 当调用时，检查Node数组是否为空，如果为空，会初始化 Node数组。
- 2 新建的Node节点，假设数组容量为 n，检查数组((n - 1) & hash) 位置是否为空，如果为空，则在该位置放上该Node节点
- 3 如果该节点不为空，检查该节点的 hash值是否和当前插入的hash值相等，并且检查两个节点的key是否相等，如果相等，则说明是同一个key，将旧节点赋值给变量Node e
- 4 否则判断该Node节点是否为TreeNode
- 5 否则：遍历该链表，当检查到为空的节点时，插入该新节点，并且检查该链表长度是否大于等于8，如果是则执行treeifyBin 方法转换为红黑树。
- 6 在遍历过程中如果找到和该节点key相等的节点，则跳出循环，将旧节点赋值给变量Node e
- 7 最后检查 onlyIfAbsent 参数 或者 e.value是否为空，如果为真，将新的value 赋值给e.value
- 8 最后 ++modCount，并判断 是否大于 threshold，如果大于，执行 resize() 方法，对数组进行扩容，为什么负载因子loadFactor 默认为0.75，
文档中的解释为：
 作为一般规则，默认负载因子（0.75）在时间和空间成本上提供了良好的权衡。负载因子数值越大，空间开销越低，
 但是会提高查找成本（体现在大多数的HashMap类的操作，包括get和put）。设置初始大小时，
 应该考虑预计的entry数在map及其负载系数，并且尽量减少rehash操作的次数。
 如果初始容量大于最大条目数除以负载因子，rehash操作将不会发生。

### 疑问
#### HashMap 为什么容量是2的幂次方，以及默认值大小为什么是 1 << 4
1）HashMap 的底层数据结构为 数组 + 链表／数组 + 红黑树，而定位数组下标的索引公式为 i = (n - 1) & hash,当初始化大小n是2的倍数时，(n - 1) & hash
等价于 n % hash，为什么不用取余法呢
- 与(&)运算 比 取余（%） 效率高
- 求余运算： a % b 就相当于 a - (a/b)*b 的运算
- 与运算就是一个指令
 所以，采用2的幂次方是为了更高效的运算

2）时间与空间的权衡，4或8太小，频繁的扩容，时间开销大，32，64或更大，浪费内存空间。

#### 为什么转为红黑树的阈值为8
数学问题，见源码注释
#### 为什么红黑树还原阈值为6
防止链表和红黑树之间的频繁转换，转换的效率非常底下／
#### 最大容量为什么是 1 << 30
由于计算机采用补码形式来表示，int 占用32位，可以向左移动31位，但是最左边的一位是符号位，用来表示正负。
## TreeMap
- 红黑树的实现
- TreeMap 中保存的记录会根据 Key 排序（默认为升序排序），因此使用 Iterator 遍历时得到的记录是排过序的
- 因为需要排序，所以TreeMap 中的 key 必须实现 Comparable 接口，否则会报 ClassCastException 异常
- TreeMap 会按照其 key 的 compareTo 方法来判断 key 是否重复
## HashTable
线程安全的HashMap，所有更改的方法都加上了sychronized 
# List
## ArrayList
- 采用数组，支持随机读写，随机访问快速，插入删除慢，默认为空数组，当为空数组增加元素时，初始化数组大小为10。当不为空数组时，为参数默认大小
- 扩容会涉及到内存拷贝，效率底下，因此最好创建时就传入数组大小,扩容会扩容一半当前大小
## LinkedList
- 采用链表实现了List,增加删除快速，随机访问慢，内存占用比ArrayList大。实现了Deque的接口，双端队列。
- 内部数据结构为双端链表Node节点，带prev和next节点
## Vector
线程安全的ArrayList
# Set
## HashSet
采用 HashMap 实现去重的集合
# concurrent
## ConcurrentHashMap
ConcurrentHashMap 总体上和 HashMap 类似，不允许 key 或者 value 为 null，而 ConcurrentHashMap 和 HashTable 是不支持为null 的。
是因为 在并发环境下，put null 时，在查找key 的过程中 无法分辨 key 没找到为 null，还是有 key 的值为null。

JDK 1.8 中，采用Node + CAS + synchronized来保证并发安全。取消类 Segment，直接用 table 数组存储键值对；
当 HashEntry 对象组成的链表长度超过 TREEIFY_THRESHOLD 时，链表转换为红黑树，提升性能。底层变更为数组 + 链表 + 红黑树。

使用 synchronized 来作为锁，是因为 synchronized 优化的足够好了，在并不是很高的情况下，synchronized 的性能和 ReentrantLock 相差不大，
但是 ReentrantLock 会消耗更多的内存。

①、重要的常量：

private transient volatile int sizeCtl;

当为负数时，-1 表示正在初始化，-N 表示 N - 1 个线程正在进行扩容；

当为 0 时，表示 table 还没有初始化；

当为其他正数时，表示初始化或者下一次进行扩容的大小。

②、数据结构：

Node 是存储结构的基本单元，继承 HashMap 中的 Entry，用于存储数据；
TreeNode 继承 Node，但是数据结构换成了二叉树结构，是红黑树的存储结构，用于红黑树中存储数据；

TreeBin 是封装 TreeNode 的容器，提供转换红黑树的一些条件和锁的控制。

③、存储对象时（put() 方法）：

如果没有初始化，就调用 initTable() 方法来进行初始化；

如果没有 hash 冲突就直接 CAS 无锁插入；

如果需要扩容，就先进行扩容；

如果存在 hash 冲突，就加锁来保证线程安全，两种情况：一种是链表形式就直接遍历

到尾端插入，一种是红黑树就按照红黑树结构插入；

如果该链表的数量大于阀值 8，就要先转换成红黑树的结构，break 再一次进入循环

如果添加成功就调用 addCount() 方法统计 size，并且检查是否需要扩容。

④、扩容方法 transfer()：默认容量为 16，扩容时，容量变为原来的两倍。

helpTransfer()：调用多个工作线程一起帮助进行扩容，这样的效率就会更高。

⑤、获取对象时（get()方法）：

计算 hash 值，定位到该 table 索引位置，如果是首结点符合就返回；

如果遇到扩容时，会调用标记正在扩容结点 ForwardingNode.find()方法，查找该结点，匹配就返回；

以上都不符合的话，就往下遍历结点，匹配就返回，否则最后就返回 null。

## CopyOnWriteArrayList

采用 ReentrantLock 和 数组实现，再每一个修改的地方都是会调用 lock 方法，然后复制一个新的数组出来进行更改，更改完成，赋值给原数组。
读取的时候直接在原数组上读取，因此，在一个线程中刚插入的数据，另一个线程可能会存在读取不到的情况。

# Queue
队列，封装了队列相关的接口，可以用于实现生产者-消费者模型
## LinkedList
如上
## PriorityQueue
优先队列，采用二叉小顶堆实现，可以用完全二叉树表示
## LinkedBlockingQueue
可以无界，容易内存溢出
采用链表实现的阻塞队列，其中节点Node只保存后继节点，也即是前插法。链表记录head和last。
采用ReentrantLock类型的putLock 和 takeLock 以及 Condition 类型的 notEmpty 和 notFull 来实现阻塞和线程安全
## ArrayBlockingQueue
有界阻塞队列
采用数组实现，采用一把锁实现的队列，循环数组，当putIndex的值到数组末尾时，将putIndex的置为0，Condition 类型的 notEmpty 和 notFull 来实现阻塞和线程安全
## PriorityBlockingQueue