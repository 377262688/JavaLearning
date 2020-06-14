package com.york.javaLearning.util.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author york
 * @create 2020-06-12 16:21
 **/
public class DefineLock extends AbstractQueuedSynchronizer implements Lock {

    @Override
    public boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lock() {
        int state = getState();
        if (state == 0) {
            if (compareAndSetState(0,1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            }
        } else {
            acquire(1);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
