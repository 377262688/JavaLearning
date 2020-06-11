# java.util 包中的工具类

# Map
 ## HashMap
 
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
 ## HashTable
 ## SortedMap
# List
## ArrayList
## LinkedList
## Vector
# Set
## HashSet
## TreeSet
## SortedSet
# Queue
## LinkedList
## PriorityQueue
## LinkedBlockingQueue
## BlockingQueue
## ArrayBlockingQueue
## PriorityBlockingQueue
# Deque

# spi