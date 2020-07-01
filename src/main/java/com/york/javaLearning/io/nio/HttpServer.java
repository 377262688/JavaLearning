package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author york
 * @create 2020-07-01 14:23
 **/
public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8002);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Request request = new Request(inputStream);
            request.parse();
            request.print();
            Response response = new Response(outputStream);
            response.setRequest(request);
            response.send();
            socket.close();
        }

    }
}
