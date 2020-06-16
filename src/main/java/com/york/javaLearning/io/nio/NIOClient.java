package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author york
 * @create 2020-06-16 11:29
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream outputStream = socket.getOutputStream();
        String s = "hello world";
        while (true) {
            outputStream.write(s.getBytes());
            outputStream.close();
        }
    }
}
