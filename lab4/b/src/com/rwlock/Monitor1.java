package com.rwlock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Monitor1 extends Thread {
    final Garden garden;
    final String path;

    public Monitor1(Garden garden, String path) {
        this.garden = garden;
        this.path = path;
    }

    @Override
    public void run() {
        FileWriter fw = null;
        try {
            File myObj = new File(path);
            myObj.createNewFile();
            fw = new FileWriter(path);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                fw = new FileWriter(path, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < garden.getRows(); ++i) {
                for (int j = 0; j < garden.getColumns(); ++j)
                {
                    try {
                        fw.write(garden.get(i, j) + " ");
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    fw.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fw.write("\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
