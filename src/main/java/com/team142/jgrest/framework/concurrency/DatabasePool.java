/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.concurrency;

import com.team142.jgrest.utils.ThreadUtils;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author just1689
 */
public class DatabasePool {

    public static final DatabasePool STANDARD_TINY_POOL = new DatabasePool(2, 20, 10);

    private final AtomicInteger current = new AtomicInteger(0);
    private final int max;
    private final int sleep;
    private final int timeoutSecondsMs;

    public DatabasePool(int size, int sleepMs, int timeoutSeconds) {
        this.max = size;
        this.sleep = sleepMs;
        this.timeoutSecondsMs = timeoutSeconds * 1000;

    }

    public void waitForNext() throws TimeoutException {
        long waitUntil = System.currentTimeMillis() + timeoutSecondsMs;

        while (System.currentTimeMillis() < waitUntil) {
            synchronized (current) {
                if (current.get() < max) {
                    int now = current.incrementAndGet();
                    if (now > max) {
                        current.decrementAndGet();
                        continue;
                    }
                    return;
                }
            }
            ThreadUtils.sleepForNow(sleep);
        }
        throw new TimeoutException();

    }

    public void giveBack() {
        current.decrementAndGet();
    }

}
