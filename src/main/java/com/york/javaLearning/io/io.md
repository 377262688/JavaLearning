# 一 概览
Java 的I/O大概可以分为以下几类
- 磁盘操作：File
- 字节操作：InputStream 和 OutPutStream
- 字符操作：Reader 和 Writer
- 对象操作：Serializable
- 网络操作：Socket
- NIO
# 二 磁盘操作
File类可以用于表示文件和目录的信息，但是它不表示文件的内容
参见 listAllFiles
# 三 字节操作

实现文件复制
参见 copyFile

Java I/O 使用了装饰器模式来实现，以 InputStream 为例：
- InputStream 是抽象组建；
- FileInputStream 是 InputStream 的子类，属于具体组建，提供了字节流的输入操作。
- FileInputStream 属于抽象装饰者，装饰者用于装饰组件，为组件提供额外功能，
例如 BufferedInputStream 为 FileInputStream 提供缓存的功能。
![avatar](https://camo.githubusercontent.com/d650ccc4ec1a0c99171582d9ccc9a5003155496f/68747470733a2f2f63732d6e6f7465732d313235363130393739362e636f732e61702d6775616e677a686f752e6d7971636c6f75642e636f6d2f39373039363934622d646230352d346363652d386432662d3163386230396634643932312e706e67)

实例化一个具有缓存功能的字节流对象时，只需要在 FileInputStream 对象上再套一层 BufferedInputStream 对象即可。
```java
FileInputStream fileInputStream = new FileInputStream(filePath);
BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
```
DataInputStream 装饰者提供了对更多数据类型进行输入的操作，比如 int、double 等基本类型。

# 四 字符操作

## 编码与解码

编码就是把字符转为字节，解码就是把字节转为字符
如果编码和解码使用不同的编码方式，那么就会出现乱码
- GBK 编码中，中文字符占2个字节，而英文字符占1个字节
- UTF-8编码中，中文字符占3个字节，而英文字符占1个字节
- UTF-16be编码中，中文和英文字符都占2个字节
UTF-16be 中的 be 指的是 Big Endian，也就是大端。相应地也有 UTF-16le，le 指的是 Little Endian，也就是小端。

Java 的内存编码使用双字节编码 UTF-16be，这不是指 Java 只支持这一种编码方式，而是说 char 这种类型使用 UTF-16be 进行编码。
char 类型占 16 位，也就是两个字节，Java 使用这种双字节编码是为了让一个中文或者一个英文都能使用一个 char 来存储。

## String 的编码方式

String 可以看成一个字符序列，可以指定一个编码方式将它编码为字节序列，也可以指定一个编码方式将一个字节序列解码为String
```java
String str1 = "中文";
```
在调用无参数 getBytes() 方法时，默认的编码方式不是 UTF-16be。双字节编码的好处是可以使用一个 char 存储中文和英文，而将 String
 转为 bytes[] 字节数组就不再需要这个好处，因此也就不再需要双字节编码。getBytes() 的默认编码方式与平台有关，一般为 UTF-8。
 
 ## Reader 和 Writer
 
 不管是磁盘传输还是网络传输，最小的存储单元都是字节，而不是字符。但是在程序中操作的通常是字符形式的数据
，因此需要提供对字符进行操作的方法。
- InputStreamReader 实现从字节流解码成字符流；
- OutputStreamWriter 实现从字符流编码成字节流。

## 实现逐行输出文本文件的内容

# 五 对象操作

## 序列化
序列化就是将一个对象转换成字节序列，方便存储和传输

- 序列化：ObjectOutputStream.writeObject()
- 反序列化：ObjectOutputStream.readObject();

不会对静态变量进行序列化，因为序列化只保存对象的状态，而静态变量是类的状态

## Serializable

需要序列化的类需要实现Serializable接口，它只是一个标准，没有任何方法需要实现。

要想将父类对象也序列化，就需要让父类也实现Serializable 接口。如果父类不实现的话的，
就 需要有默认的无参的构造函数。在父类没有实现 Serializable 接口时，虚拟机是不会序列化父对象的，
而一个 Java 对象的构造必须先有父对象，才有子对象，反序列化也不例外。所以反序列化时，为了构造父对象，
只能调用父类的无参构造函数作为默认的父对象。因此当我们取父对象的变量值时，它的值是调用父类无参构造函数后的值。
如果你考虑到这种序列化的情况，
在父类无参构造函数中对变量进行初始化，否则的话，父类变量值都是默认声明的值，如 int 型的默认是 0，string 型的默认是 null。

Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，
在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。

### 利用序列化对敏感字段加密

在序列化过程中，虚拟机会试图调用对象类里的 writeObject 和 readObject 方法，进行用户自定义的序列化和反序列化，
如果没有这样的方法，则默认调用是 ObjectOutputStream 的 defaultWriteObject 方法以及 ObjectInputStream 的 defaultReadObject 方法。
用户自定义的 writeObject 和 readObject 方法可以允许用户控制序列化的过程，比如可以在序列化的过程中动态改变序列化的数值。

多次写入一个序列化文件同一个对象，文件大小只增加了5个字节，
Java 序列化机制为了节省磁盘空间，具有特定的存储规则，当写入文件的为同一对象时，
并不会再将对象的内容进行存储，而只是再次存储一份引用，上面增加的 5 字节的存储空间就是新增引用和一些控制信息的空间。
反序列化时，恢复引用关系。该存储规则极大的节省了存储空间。

当读取时，因为只是保存了第二次写入的引用，所以读取出来的都是第一个对象。