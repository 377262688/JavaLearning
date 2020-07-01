package com.york.javaLearning.io.nio;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author york
 * @create 2020-07-01 14:27
 **/
public class Response {

    public OutputStream outputStream;

    public Request request;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void send() throws IOException {
        String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 23\r\n" +
                "\r\n" +
                "<h1>File Not Found</h1>";
        outputStream.write(errorMessage.getBytes());

    }
}
