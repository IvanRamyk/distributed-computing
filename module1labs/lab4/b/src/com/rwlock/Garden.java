package com.rwlock;

public class Garden {
    int[][] field;
    final int rows;
    final int columns;
    final ReadWriteLock lock;


    public Garden(int rows, int columns, ReadWriteLock lock) {
        this.rows = rows;
        this.columns = columns;
        this.lock = lock;
        field = new int[rows][columns];
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns; ++j)
                field[i][j] = 1;
    }

    public int getRows() {
        return rows;
    }

    public void set(int i, int j, int value) throws InterruptedException {
        lock.writeLock();
        field[i][j] = value;
        lock.writeUnLock();
    }

    public int get(int i, int j) throws InterruptedException {
        lock.readLock();
        int val = field[i][j];
        lock.readUnLock();
        return val;
    }

    public int getColumns() {
        return columns;
    }
}
