/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.generic;

import com.team142.jgrest.framework.concurrency.DatabasePool;
import com.team142.jgrest.framework.nio.HttpClient;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Updater {

    public static void sync(DatabasePool databasePool, String url, Object o) throws SocketTimeoutException, TimeoutException {
        HttpClient.doPatchAndForget(databasePool, url, o);
    }

    public static void async(DatabasePool databasePool, String url, Object o) {
        new Thread(() -> {
            try {
                HttpClient.doPatchAndForget(databasePool, url, o);
            } catch (SocketTimeoutException | TimeoutException ex) {
                Logger.getLogger(Deleter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

}
