package sbu.cs.Semaphore;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Operator extends Thread {

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            Controller.sem.acquire();
            for (int i = 0; i < 10; i++) {
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
                try {
                    sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LocalTime currentTime = LocalTime.now ();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("HH:mm:ss");
            System.out.println (Thread.currentThread ().getName () + " accessed the resource at " + currentTime.format (formatter));
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            Controller.sem.release();
        }
    }
}
