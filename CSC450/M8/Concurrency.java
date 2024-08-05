package CSC450.M8;

import java.util.concurrent.locks.ReentrantLock;

public class Concurrency {
     volatile static int counter = 0;

     static ReentrantLock lock = new ReentrantLock();
     
     // Provides a Runnable object. The Runnable interface defines a single method, 
     // run, meant to contain the code executed in the thread. that counts up to 20
     public static class CountToTwenty implements Runnable{
          public void run() {
               lock.lock();
               try {
                    while (counter < 20){
                         System.out.println("Counter is " + counter);
                         try {
                              // sleep is used to suspend execution of a thread in this case to 
                              // simulate work in milliseconds
                              Thread.sleep(400);
                              ++counter;
                         // InterruptedException is an exception that sleep throws when another 
                         // thread interrupts the current thread while sleep is active.
                         } catch (InterruptedException e) {
                              e.printStackTrace();
                         }
                    } 
               }finally {
                    lock.unlock();
               }
          }
     }

     // Provides a Runnable object. The Runnable interface defines a single method, 
     // run, meant to contain the code executed in the thread. that counts down to 0
     public static class CountToZero implements Runnable {
          public void run() {
               lock.lock();
               try {
                    while (counter >= 0){
                         System.out.println("Counter is " + counter);
                         try {
                              // sleep is used to suspend execution of a thread in this case to 
                              // simulate work in milliseconds
                              Thread.sleep(400);
                              --counter;
                         // InterruptedException is an exception that sleep throws when another 
                         // thread interrupts the current thread while sleep is active.
                         } catch (InterruptedException e) {
                              e.printStackTrace();
                         }
                    }
               }finally {
                    lock.unlock();
               }
          }
     }

     public static void main(String[] args) {
          // creates a Runnable object with thread constructor
          Thread firstThread = new Thread(new CountToTwenty());
          // starts the first thread to count up to 20
          firstThread.start();
          // Wait for the first thread to complete before starting the second thread
          try {
               //The join method allows one thread to wait for the completion of another
               firstThread.join();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
  
          // then creates another Runnable object with thread constructor
          Thread secondThread = new Thread(new CountToZero());
          // starts the second thread to count down to 0
          secondThread.start();
      }
}
