package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author york
 * @create 2020-06-16 11:29
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8889));
        socket.setKeepAlive(true);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        socket.setKeepAlive(true);
        String s = "hello world";
        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.write(s.getBytes());

        outputStream.write(s.getBytes());


        int n;
        byte[] bytes = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while ((n = inputStream.read(bytes)) > 0) {
            System.out.println("read");
            char[] chars = new char[n];
            for (int i = 0; i < n; i++) {
                chars[i] = (char) bytes[i];
            }
            stringBuilder.append(chars);
        }
        System.out.println(stringBuilder.toString());
        socket.close();

    }
}
