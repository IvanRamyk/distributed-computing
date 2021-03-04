package com.rwlock;
import com.rwlock.threads.AddPhoneThread;
import com.rwlock.threads.SearchPersonThread;
import com.rwlock.threads.SearchPhoneThread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        AddPhoneThread addPhoneThread1 = new AddPhoneThread(lock, "phones", "Ivanov", "12");
        AddPhoneThread addPhoneThread2 = new AddPhoneThread(lock, "phones", "Ivanov", "10");
        addPhoneThread1.start();
        addPhoneThread2.start();
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SearchPhoneThread phoneSearcher = new SearchPhoneThread(lock, "phones","Ivanov");
        SearchPersonThread searchPersonThread = new SearchPersonThread(lock, "phones","12");
        searchPersonThread.start();
        phoneSearcher.start();
        //Deleter deleter = new Deleter(lock, "D:\\Labs\\DC_MOD1_LAB4_a\\in.txt","Andy","8");
        //deleter.start();
    }
}
