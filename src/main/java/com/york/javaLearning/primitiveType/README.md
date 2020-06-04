# java 基本数据类型及其包装类型

## 分类

### 整型 
- byte (8位 1字节 -2^7 - (2^7 - 1)) 
- short(16位 2字节 -2^15 - (2^15 - 1)) 
- int(16位 4字节 -2^31 - (2^31 - 1) 
- long(64位 8字节 -2^63 - (2^63 - 1))
### 浮点型 
- float(32位 4字节 -2^7 - (2^7 - 1)) 
- double(64位 8字节 -2^7 - (2^7 - 1))
### 逻辑型 
- boolean(8位 1字节 true/false)
### 字符型 
- char(16位 2字节 0-2^16)

## 对应包装类型

- 整型 Byte Short Integer Long
- 浮点型 Float Double
- 逻辑型 Boolean
- 字符型 Character

## 缓存
其中 Byte,Short,Integer,Long 默认采用缓存来纪录-128～127之间的数字，
在使用 Integer i = 3 的时候，实际是调用了 Integer.valueOf(3) 来获取缓存的，
因此并不是新建对象。
 new Integer(127) == new Integer(127) 为false,因为是两个不同的对象，和缓存无关
而 new Integer(128) == new Integer(128) 为false

## 拆箱与装箱

- 装箱 调用 Integer i = 3 的时候实际调用 Integer.valueOf(3)
- 拆箱 Integer i = 3，int j = i 的时候，实际调用的 Integer.intValue()

## == 与 equals()

### ==
- 基本数据类型：byte,short,char,int,long,float,double,boolean。他们之间的比较，应用双等号（==）,比较的是他们的值。
- 引用数据类型：当他们用（==）进行比较的时候，比较的是他们在内存中的存放地址（确切的说，是堆内存地址）。
### equals()
JAVA当中所有的类都是继承于Object这个超类的，在Object类中定义了一个equals的方法，equals的源码是这样写的：
```java
    public boolean equals(Object obj) {
        return (this == obj);
    }
```
可以看到，这个方法的初始默认行为是比较对象的内存地址值，一般来说，意义不大。所以，在一些类库当中这个方法被重写了，如String、Integer、Date。在这些类当中equals有其自身的实现（一般都是用来比较对象的成员变量值是否相同），而不再是比较类在堆内存中的存放地址了。
所以说，对于复合数据类型之间进行equals比较，在没有覆写equals方法的情况下，他们之间的比较还是内存中的存放位置的地址值，跟双等号（==）的结果相同；如果被复写，按照复写的要求来。


