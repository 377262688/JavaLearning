package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author york
 * @create 2020-06-28 15:58
 **/
public class BlockServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1",8889));
        System.out.println("socket 启动成功");
        while (true) {
            Socket socket = serverSocket.accept();
            socket.setKeepAlive(true);
            System.out.println("接收到socket");
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            OutputStream outputStream = socket.getOutputStream();
            int read;
            while ((read = inputStream.read(bytes)) > 0) {
                System.out.println("read");
                char[] chars = new char[read];
                outputStream.write(bytes);
                for (int i = 0; i < read; i++) {
                    chars[i] = (char) bytes[i];
                }
                stringBuilder.append(chars);
            }
            System.out.println(stringBuilder.toString());
            outputStream.close();
        }

    }
}
