package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample {

    public void func2() {
        synchronized (SynchronizedExample.class) {
            for (int j = 0; j < 1000; j++) {
                System.out.println("func" + j);
            }
        }
    }
    synchronized static void sychTest() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("sychTest" + i);
        }
    }
    public static void main(String[] args) {
        SynchronizedExample e1 = new SynchronizedExample();
        SynchronizedExample e2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> e1.func2());
        executorService.execute(() -> sychTest());
        executorService.shutdown();
    }
}
