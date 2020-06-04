# String 类详解

String 类型是 JAVA 中一种特殊的类型，JAVA 内存模型中有一块为方法区（JAVA8 中改为Metaspace），
其中有一块是运行时常量池（JAVA7 将字符串常量池放在堆里，而其他常量还是放在方法区（Metaspace）堆外内存），
String 常量就是在常量池中，字符串常量池在内存中是以表（hashtable）的形式存在，有一张固定长度 
CONSTANT_String_info表用来存储文字字符串值，注意：该表只存储文字字符串值，不存储符号引用。
常量池保存很多String对象，并且可以被共享使用，因此提高了效率。

## 内部实现
String 内部是由一个final char[],并且没有暴露相关的修改数组的方法来实现不可变，
并且内部封装了一些比如replace(),substring(),split()等方法，这些方法返回的都是新建一个String对象
## 不可变

在代码中可以创建多个某一个String 对象的别名，但是这些别名引用的地址是相同的。

## String 的连接

- JAVA 虚拟机处理 String 的连接符 + 时会有不同的处理

如果都是字符常量，那么只会生成一个。
如果有变量，那么会调用StringBuilder，最后调用Sb的tostring。
连接符两边只要有一个不是字符串常量，那就是说明那个变量是个地址的引用，引用指向的值编译时无法知道的。
String a = "123" （生成一个字符串常量）
String a = "123" + b （调用StringBuilder）

- String a="a"+"b"+"c"在内存中创建几个对象？

javac会进行常量折叠，全字面量字符串相加是可以折叠为一个字面常量，而且是进入常量池的。
优化进行在编译器编译.java到bytecode时，通过编译器优化后，得到的效果是String a="abc" 。
此时，如果字符串常量池中存在abc，则该语句并不会创建对象，只是讲字符串常量池中的引用返回而已。
字符串常量池存放的是对象引用，不是对象。在Java中，对象都创建在堆内存中。
如果字符串常量池中不存在abc，则会创建并放入字符串常量池，并返回引用，此时会有一个对象进行创建。

- String.intern()
  String对象的实例调用intern方法后，可以让JVM检查常量池，
  如果没有实例的value属性对应的字符串序列比如"123"(注意是检查字符串序列而不是检查实例本身)，
  就将本实例放入常量池，如果有当前实例的value属性对应的字符串序列"123"在常量池中存在，
  则返回常量池中"123"对应的实例的引用而不是当前实例的引用，即使当前实例的value也是"123"。
 
## StringBuilder 和 StringBuffer

带有字符串变量的连接操作（+），JVM 内部采用的是StringBuilder 来实现的，
而之前这个操作是采用 StringBuffer 实现的。

StringBuilder 是线程不安全的，而StringBuffer是用 synchronized 关键字实现线程安全