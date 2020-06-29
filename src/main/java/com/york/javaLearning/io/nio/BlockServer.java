package com.york.javaLearning.io.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author york
 * @create 2020-06-28 15:58
 **/
public class BlockServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1",8889));
        System.out.println("socket 启动成功");
        while (true) {
            Socket socket = serverSocket.accept();
            socket.setKeepAlive(true);
            System.out.println("接收到socket");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("收到");
            printWriter.flush();
            new Thread(() -> {
                int i = 0;
                while (true) {
                    printWriter.println(i);
                    printWriter.flush();
                    System.out.println("传输数据" + i);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }).start();
            String read;
            while ((read = reader.readLine()) != null) {
                System.out.println("read line ------");
                System.out.println(read);
            }



        }

    }
}
