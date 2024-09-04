package exercises01;

import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Printer p = new Printer();
        Thread t1 = new Thread(() -> {
            for (;;){
                p.print();
            }
        });
        Thread t2 = new Thread(() -> {
            for (;;){
                p.print();
            }
        });

        t1.start(); t2.start();

        try { t1.join(); t2.join(); }
        catch (InterruptedException exn) { 
            System.out.println("Some thread was interrupted");
        }

    }

    public void print(){
        lock.lock();
        try {
            System.out.print("-"); // 1
            try {
                Thread.sleep(50); // 2
            } catch(Exception e) {}
            System.out.print("|"); // 3
        } 
        finally {
            lock.unlock();
        }
    }
    // Terminal output
    // --|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-||--|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-||--|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-||--|-|-|-|-|-|-
}
