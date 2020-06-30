package com.york.javaLearning.io.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * Created by york on 2019/4/9.
 */
public class EchoClient {

    private LinkedList<ByteBuffer> outq;

    public EchoClient() {
        outq = new LinkedList<>();
    }

    public void enqueue(ByteBuffer bb) {
        outq.addFirst(bb);
    }
}
