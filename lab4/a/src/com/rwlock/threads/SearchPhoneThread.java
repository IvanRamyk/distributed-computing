package com.rwlock.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class SearchPhoneThread extends Thread {
    protected String path;
    protected String person;
    protected ArrayList<String> phones = new ArrayList<>();
    private final ReentrantReadWriteLock lock;

    public SearchPhoneThread(ReentrantReadWriteLock lock, String path, String person) {
        this.path = path;
        this.person = person;
        this.lock = lock;
    }

    public String getPerson() {
        return person;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    @Override
    public void run() {
        lock.readLock().lock();
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" - ", 2);
                if (data[0].equals(person)) {
                    phones.add(data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Phone numbers of " + person + ":");
        for (String phone : phones)
            System.out.print(phone + " ");
        lock.readLock().unlock();
    }
}
