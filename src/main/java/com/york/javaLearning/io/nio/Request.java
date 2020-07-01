package com.york.javaLearning.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author york
 * @create 2020-07-01 14:26
 **/
public class Request {

    private InputStream inputStream;

    /**
     * 请求方法 GET/POST/PUT/DELETE/OPTION...
     */
    private String method;
    /**
     * 请求的uri
     */
    private String uri;
    /**
     * http版本
     */
    private String version;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求参数相关
     */
    private String message;

    private StringBuilder ori = new StringBuilder();

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        decodeRequestLine(reader);
        decodeRequestHeader(reader);
        decodeRequestMessage(reader);
        System.out.println("解析完成");
    }

    private void decodeRequestLine(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        ori.append(requestLine).append("\n");
        System.out.println("request line:" + requestLine);
        String[] requestLineData = requestLine.split(" ");
        this.method = requestLineData[0];
        this.uri = requestLineData[1];
        this.version = requestLineData[2];
    }

    private void decodeRequestHeader(BufferedReader reader) throws IOException {
        headers = new HashMap<>(16);
        String line = reader.readLine();
        String[] kv;
        while (!"".equals(line)) {
            ori.append(line).append("\n");
            kv = line.split(":");
            assert kv.length == 2;
            headers.put(kv[0].trim(),kv[1].trim());
            line = reader.readLine();
        }
    }

    private void decodeRequestMessage(BufferedReader reader) throws IOException {
        int contentLen = Integer.parseInt(headers.getOrDefault("Content-Length","0"));
        if (contentLen == 0) {
            return;
        }
        char[] message = new char[contentLen];
        reader.read(message);
        this.message = new String(message);
        ori.append(message).append("\n");
    }

    public void print() {
        System.out.println(ori.toString());
    }
}
