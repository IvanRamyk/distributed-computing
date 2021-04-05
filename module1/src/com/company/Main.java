package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class Book {
    ReentrantLock lock;
    private String name;
    private int id;
    boolean isAvailable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        lock.lock();
        this.name = name;
        lock.unlock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        lock.lock();
        this.id = id;
        lock.unlock();
    }

    public Book( int id, String name) {
        this.name = name;
        this.id = id;
        this.lock = new ReentrantLock();
        this.isAvailable = true;
    }

    public boolean takeBook() {
        lock.lock();
        if (!this.isAvailable) {
            lock.unlock();
            return false;
        }
        this.isAvailable = false;
        lock.unlock();
        return true;
    }

    public void returnBook() {
        lock.lock();
        this.isAvailable = true;
        lock.unlock();
    }
}

class Library {
    private List<Book> lstBooksLibrary = new ArrayList<>();

    public List<Book> getLstBooksLibrary() {
        return lstBooksLibrary;
    }

    public Library(List<Book> lstBooksLibrary) {
        this.lstBooksLibrary = lstBooksLibrary;
    }

    public void setLstBooksLibrary(List<Book> lstBooksLibrary) {
        this.lstBooksLibrary = lstBooksLibrary;
    }
}

class Client extends Thread {
    private final int cntBooks;
    private final Library library;
    private final int id;

    Client(int id, Library library, int books) {
        this.cntBooks = books;
        this.id = id;
        this.library = library;
    }

    @Override
    public void run() {
        super.run();
        Random random = new Random();
        int booksInLib = library.getLstBooksLibrary().size();
        for (int t = 0; t < 100; ++t) {
            List<Book> readInside = new ArrayList<>();
            List<Book> takeHome = new ArrayList<>();
            for (int j = 0; j < cntBooks; ++j) {
                int bookInd = random.nextInt(booksInLib);
                boolean readInLib = random.nextBoolean();
                if (library.getLstBooksLibrary().get(bookInd).takeBook()) {
                    System.out.println("Client " + id + " takes book " + library.getLstBooksLibrary().get(bookInd).getName());
                    if (readInLib)
                        readInside.add((library.getLstBooksLibrary().get(bookInd)));
                    else
                        takeHome.add((library.getLstBooksLibrary().get(bookInd)));
                } else {
                    System.out.println("Book " + library.getLstBooksLibrary().get(bookInd).getName() + " is not available");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Client " + this.id +  " is reading in lib..");
            for (Book book : readInside) {
                book.returnBook();
                System.out.println("Book " + book.getName() + " is returned");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Client " + this.id +  " is reading at home..");
            for (Book book : takeHome) {
                book.returnBook();
                System.out.println("Book " + book.getName() + " is returned");
            }
        }
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book A"));
        books.add(new Book(2, "Book B"));
        books.add(new Book(3, "Book C"));
        books.add(new Book(4, "Book D"));
        books.add(new Book(5, "Book E"));
        Library library = new Library(books);

        for (int i = 0; i < 3; ++i) {
            Client client = new Client(i, library, 2);
            client.setDaemon(false);
            client.start();
        }

        Thread.sleep(20000);

    }
}
