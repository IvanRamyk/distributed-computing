package com.rwlock.threads;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AddPhoneThread extends Thread {
    private final String path;
    private final String person;
    private final String phone;
    private final ReentrantReadWriteLock lock;

    public AddPhoneThread(ReentrantReadWriteLock lock, String path, String person, String phone) {
        this.lock = lock;
        this.path = path;
        this.person = person;
        this.phone = phone;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        try {
            FileWriter fw = new FileWriter(path, true); //the true will append the new data
            fw.write(person + " - " + phone + "\n");//appends the string to the file
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();
    }
}
