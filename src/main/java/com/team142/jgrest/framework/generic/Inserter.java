
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

/**
 *
 * @author just1689
 */
public class Inserter {

    public static void insert(DatabasePool databasePool, String url, Object o) throws SocketTimeoutException, TimeoutException {
        HttpClient.doPostAndForget(databasePool, url, o);
    }

}
