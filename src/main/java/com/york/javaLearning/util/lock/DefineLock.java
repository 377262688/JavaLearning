package com.york.javaLearning.util.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author york
 * @create 2020-06-12 16:21
 **/
public class DefineLock extends AbstractQueuedSynchronizer {

    @Override
    public boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
}
