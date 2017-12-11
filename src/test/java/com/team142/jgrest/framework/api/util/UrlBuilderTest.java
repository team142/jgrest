/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;
import static org.junit.Assert.*;

/**
 *
 * @author just1689
 */
public class UrlBuilderTest {

    private static final String URL = "http://domain.com";
    private static final Database TEST_DATABASE = new Database(URL, null);

    /**
     * Test of getUrl method, of class UrlBuilder.
     */
    @org.junit.Test
    public void testGetUrl_Database_String() {
        System.out.println("Does the database and table produce a valid ref?");
        String table = "table";
        String expResult = "http://domain.com/table";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table);
        assertEquals(expResult, result);

    }

    /**
     * Test of getUrl method, of class UrlBuilder.
     */
    @org.junit.Test
    public void testGetUrl_4args() {
        System.out.println("getUrl");
        String table = "table";
        Condition condition = new Condition("id", "eq", "1");
        boolean onlyOne = false;
        String expResult = "http://domain.com/table?id=eq.1";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table, condition, onlyOne);

        if (!result.equals(expResult)) {
            System.out.println("Expected");
            System.out.println(expResult);
            System.out.println("Instead got");
            System.out.println(result);
        }

        assertEquals(expResult, result);

    }

    /**
     * Test of getUrl method, of class UrlBuilder.
     */
    @org.junit.Test
    public void testGetUrl_Encoding() {
        System.out.println("Testing getUrl by condition with encoding");
        String table = "table";
        Condition condition = new Condition("title", "eq", "Penguins are majestic");
        boolean onlyOne = false;
        String expResult = "http://domain.com/table?id=eq.1";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table, condition, onlyOne);

        if (!result.equals(expResult)) {
            System.out.println("Expected");
            System.out.println(expResult);
            System.out.println("Instead got");
            System.out.println(result);
        }

        assertEquals(expResult, result);

    }


}
