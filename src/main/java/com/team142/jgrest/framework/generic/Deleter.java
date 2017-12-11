/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.generic;

import com.team142.jgrest.framework.nio.HttpClient;
import com.team142.jgrest.model.Database;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class Deleter {

    public static void sync(Database database, String url) throws SocketTimeoutException, TimeoutException {
        HttpClient.doDeleteAndForget(database, url);
    }

    public static void async(Database database, String url) {
        new Thread(() -> {
            try {
                HttpClient.doDeleteAndForget(database, url);
            } catch (SocketTimeoutException | TimeoutException ex) {
                Logger.getLogger(Deleter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

}
