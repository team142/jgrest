/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 */
public class ThreadUtils {

    public static void sleepForNow(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
