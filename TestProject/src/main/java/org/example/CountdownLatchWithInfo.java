package org.example;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchWithInfo<T> {
    private final CountDownLatch latch;
    private T information;

    public CountdownLatchWithInfo(int count) {
        this.latch = new CountDownLatch(count);
    }

    public void countDown() {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public void setInformation(T info) {
        this.information = info;
    }

    public T getInformation() {
        return information;
    }
}
