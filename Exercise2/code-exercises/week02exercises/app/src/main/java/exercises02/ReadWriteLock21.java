//our code goes here
package exercises02;
// chaos
public class ReadWriteLock21 {
    boolean writer = false;
    int readsAcquired = 0;
    int readsReleased = 0;


    public synchronized void readLock() {
        try {
            while(writer) this.wait();
            readsAcquired++;
        } catch (InterruptedException e) {}
    }

    public synchronized void readUnlock() {
        readsReleased++;
        if(readsReleased == readsAcquired) this.notifyAll();
    }

    public synchronized void writeLock() {
        try {
            while(writer) this.wait();
            writer = true;
            while (readsAcquired != readsReleased) this.wait();
        } catch (InterruptedException e) {}
    }

    public synchronized void writeUnlock() {
        writer = false; 
        this.notifyAll();
    }

    public static void main(String[] args) {
        ReadWriteLock21 m = new ReadWriteLock21();
        for (int i = 0; i < 10; i++) {
            // start a reader
            new Thread(() -> {
            m.readLock();
            System.out.println(" Reader " + Thread.currentThread().getId() + " started reading");
            // read
            System.out.println(" Reader " + Thread.currentThread().getId() + " stopped reading");
            m.readUnlock();
        }).start();
        // start a writer
        new Thread(() -> {
            m.writeLock();
            System.out.println(" Writer " + Thread.currentThread().getId() + " started writing");
            // write
            System.out.println(" Writer " + Thread.currentThread().getId() + " stopped writing");
            m.writeUnlock();
        }).start();
        }
    }
}
