package com.york.javaLearning.并发编程实战;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @author yangjianzhong
 * @create 2021-04-12 下午10:17
 **/
public abstract class SocketUseingTask<T> implements CancellableTask<T> {

    private Socket socket;

    public SocketUseingTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void cancel() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUseingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
