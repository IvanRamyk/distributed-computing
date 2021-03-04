package com.rwlock.threads;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class SearchPersonThread extends Thread {
    protected String path;
    protected String person;
    protected String phone;
    private final ReentrantReadWriteLock lock;

    public SearchPersonThread(ReentrantReadWriteLock lock, String path, String phone) {
        this.lock = lock;
        this.path = path;
        this.phone = phone;
    }

    public String getPerson() {
        return person;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public void run() {
        lock.readLock().lock();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String data[] = line.split(" - ", 2);
                if (data[1].equals(phone)) {
                    person = data[0];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (person != null)
            System.out.println("Phone number " + phone + " belongs to " + person);
        lock.readLock().unlock();
    }
}
