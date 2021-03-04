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
        System.out.println("Add record thread lock.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(person + " - " + phone + "\n");
            System.out.printf("Phone %s was added\n", phone);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Add record thread unlock.");
        lock.writeLock().unlock();
    }
}
