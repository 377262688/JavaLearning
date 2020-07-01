package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author york
 * @create 2020-06-30 22:11
 **/
public class ChatServer {

    private static Selector selector;

    private static void startServer() throws IOException {
        selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(8001);
        ssc.socket().bind(isa);

        ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    doAccept(serverSocketChannel);
                } else if (key.isValid() && key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    doRead(socketChannel);
                }
            }
        }

    }

    private static void doAccept(ServerSocketChannel sc) throws IOException {
        SocketChannel clientChannel = sc.accept();
        clientChannel.socket().setKeepAlive(true);
        clientChannel.configureBlocking(false);
        SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);


    }

    private static void doRead(SocketChannel sc) {

    }

    public static void main(String[] args) {

    }

}
