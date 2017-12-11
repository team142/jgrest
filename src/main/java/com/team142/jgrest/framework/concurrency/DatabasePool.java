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

    private static final long TIMEOUT_MS = 1 * 60 * 1000;
    private final AtomicInteger current = new AtomicInteger(0);
    private final int max;
    private final int sleep;

    public DatabasePool(int size, int sleepMs) {
        this.max = size;
        this.sleep = sleepMs;

    }

    public void waitForNext() throws TimeoutException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TIMEOUT_MS) {
            synchronized (current) {
                if (current.get() < max) {
                    int now = current.incrementAndGet();
                    if (now > max) {
                        current.decrementAndGet();
                        continue;
                    }
                    break;
                }
            }
        }
        ThreadUtils.sleepForNow(sleep);
        throw new TimeoutException();

    }

    public void giveBack() {
        current.decrementAndGet();
    }

}
