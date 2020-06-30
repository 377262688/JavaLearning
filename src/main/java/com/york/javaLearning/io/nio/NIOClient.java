package com.york.javaLearning.io.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author york
 * @create 2020-06-16 11:29
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888));
        socket.setKeepAlive(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setKeepAlive(true);
        String s = "hello world";
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(s);
        printWriter.flush();
        String input = reader.readLine();
        System.out.println(input);
        new Thread(() -> {
            while (true) {
                printWriter.println("dsasa");
                printWriter.flush();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        printWriter.print("helleo 1");
        printWriter.println("helleo 2");
        printWriter.flush();

        String read;
        while ((read = reader.readLine()) != null) {
            System.out.println(read);
        }


    }
}
