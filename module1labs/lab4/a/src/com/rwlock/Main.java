package com.rwlock;
import com.rwlock.threads.AddPhoneThread;
import com.rwlock.threads.DeletePhoneThread;
import com.rwlock.threads.SearchPersonThread;
import com.rwlock.threads.SearchPhoneThread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        AddPhoneThread addPhoneThread1 = new AddPhoneThread(lock, "phones", "Ivanov", "12");
        AddPhoneThread addPhoneThread2 = new AddPhoneThread(lock, "phones", "Ivanov", "10");
        DeletePhoneThread deletePhoneThread2 = new DeletePhoneThread(lock, "phones", "10");
        DeletePhoneThread deletePhoneThread1 = new DeletePhoneThread(lock, "phones", "12");
        SearchPhoneThread searchPhoneThread1 = new SearchPhoneThread(lock, "phones","Ivanov");
        SearchPhoneThread searchPhoneThread2 = new SearchPhoneThread(lock, "phones","Sergey");
        SearchPhoneThread searchPhoneThread3 = new SearchPhoneThread(lock, "phones","Ivanov");
        SearchPhoneThread searchPhoneThread4 = new SearchPhoneThread(lock, "phones","Sergey");
        SearchPersonThread searchPersonThread = new SearchPersonThread(lock, "phones","12");
        addPhoneThread1.start();
        addPhoneThread2.start();
        searchPersonThread.start();
        searchPhoneThread1.start();
        searchPhoneThread2.start();
        deletePhoneThread1.start();
        deletePhoneThread2.start();
        searchPhoneThread3.start();
        searchPhoneThread4.start();
    }
}
