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

    @Override
    public void run() {
        lock.readLock().lock();
        System.out.println("Search phones thread lock.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        StringBuilder answer = new StringBuilder();
        answer.append("Phone numbers of ").append(person).append(": ");
        for (String phone : phones)
            answer.append(phone).append(" ");
        System.out.println(answer);
        System.out.println("Search person thread unlock.");
        lock.readLock().unlock();
    }
}
