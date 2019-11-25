package com.xcf.util.concurrent;

import java.io.Closeable;
import java.util.concurrent.locks.Lock;

/**
 * 自动释放锁资源的工具类
 */
public class AutoLock implements Closeable {

    private final Lock lock;

    public AutoLock(Lock lock) {
        this.lock = lock;
        this.lock.lock();
    }

    @Override
    public void close() {
        this.lock.unlock();
    }
}
