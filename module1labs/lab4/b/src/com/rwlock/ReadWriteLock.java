package com.rwlock;

public class ReadWriteLock {
    private int readersCount;
    private int writersCount;
    private int writeRequestsCount;

    public synchronized void writeLock() throws InterruptedException {
        ++writeRequestsCount;
        while (readersCount > 0 || writersCount > 0)
            wait();
        --writeRequestsCount;
        ++writersCount;
    }

    public synchronized void writeUnLock()
    {
        --writersCount;
        notifyAll();
    }

    public synchronized void readLock() throws InterruptedException {
        while (writeRequestsCount > 0 || writersCount > 0)
            wait();
        ++readersCount;
    }

    public synchronized void readUnLock()
    {
        --readersCount;
        notifyAll();
    }
}
