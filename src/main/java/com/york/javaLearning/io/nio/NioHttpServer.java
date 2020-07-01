package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author york
 * @create 2020-07-01 15:43
 **/
public class NioHttpServer {

    private static Selector selector;

    public static void main(String[] args) throws IOException {
        selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ServerSocket serverSocket = ssc.socket();
        ssc.configureBlocking(false);
        SocketAddress address = new InetSocketAddress("127.0.0.1",8003);
        serverSocket.bind(address);

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (;;) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    doAccept(key);
                } else if (key.isReadable()) {
                    doRead(key);
                } else if (key.isWritable()) {
                    doWrite(key);
                }
            }
        }
    }

    private static void doWrite(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        System.out.println("doWrite");
        String s = "HTTP/1.1 404 File Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 23\r\n" +
                "\r\n" +
                "<h1>File Not Found</h1>";
        byte[] bytes = s.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        System.out.println(bytes.length);
        buffer.put(bytes);
        socketChannel.write(buffer);

    }

    private static void doRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder stringBuilder = new StringBuilder();
        int read = socketChannel.read(buffer);
        while (read != 0) {
            System.out.println(read);
            buffer.flip();
            while (buffer.hasRemaining()) {
                stringBuilder.append((char) buffer.get());
            }
            buffer.clear();
            read = socketChannel.read(buffer);
        }
        System.out.println(stringBuilder.toString());
        socketChannel.register(selector,SelectionKey.OP_WRITE);
    }

    private static void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);
        sc.register(selector,SelectionKey.OP_READ);
        System.out.println("accepted");
    }
}
