/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.concurrency;

import com.team142.jgrest.utils.ThreadUtils;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
public class DatabasePool {

    public static final DatabasePool STANDARD_TINY_POOL = new DatabasePool(2, 20, 10, 2000, 1000);

    private final AtomicInteger current = new AtomicInteger(0);
    private final int max;
    private final int sleep;
    private final int timeoutSecondsMs;
    private final long connectionTimeout;
    private final int readTimeout;

    /**
     *
     * @param size The maximum number of active connections
     * @param sleepMs The time to sleep between waiting for a turn
     * @param timeoutSeconds How long before denying a request for connections
     * @param connectionTimeoutMs HTTP connection timeout
     * @param readTimeoutMs HTTP response read timeout (Change for long running)
     */
    public DatabasePool(int size, int sleepMs, int timeoutSeconds, long connectionTimeoutMs, int readTimeoutMs) {
        this.max = size;
        this.sleep = sleepMs;
        this.timeoutSecondsMs = timeoutSeconds * 1000;
        this.connectionTimeout = connectionTimeoutMs;
        this.readTimeout = readTimeoutMs;

    }

    public void waitForNext() throws TimeoutException {
        long waitUntil = System.currentTimeMillis() + timeoutSecondsMs;

        while (System.currentTimeMillis() < waitUntil) {
            synchronized (current) {
                if (current.get() < max) {
                    int now = current.incrementAndGet();
                    if (now > max) {
                        current.decrementAndGet();
                        System.err.println("This should never happen... "
                                + "threadpool overbooked");
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
