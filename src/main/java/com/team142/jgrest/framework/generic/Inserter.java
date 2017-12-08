/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.generic;

import com.team142.jgrest.framework.nio.HttpUtils;
import java.net.SocketTimeoutException;

/**
 *
 * @author just1689
 */
public class Inserter {

    public static void insert(String url, Object o) throws SocketTimeoutException {
        HttpUtils.doPostAndForget(url, o);
    }

}
