/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.ConditionBundle;
import com.team142.jgrest.model.Database;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    public void testGetUrl_Simple() {
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
    public void testGetUrl_BasicCondition() throws UnsupportedEncodingException {
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
    public void testGetUrl_BasicCondition2() throws UnsupportedEncodingException {
        System.out.println("getUrl");
        String table = "people";
        Condition condition = new Condition("age", "lt", "13");
        boolean onlyOne = false;
        String expResult = "http://domain.com/people?age=lt.13";
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
     *
     * @throws java.io.UnsupportedEncodingException
     */
    @org.junit.Test
    public void testGetUrl_SimpleCompoundQuery() throws UnsupportedEncodingException {
        System.out.println("getUrl");
        String table = "people";
        Condition condition1 = new Condition("age", "gte", "18");
        Condition condition2 = new Condition("student", "is", "true");
        ConditionBundle bundle = new ConditionBundle("and", condition1, condition2);

        boolean onlyOne = false;
        String expResult = "http://domain.com/people?and=(age.gte.18,student.is.true)";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table, bundle, onlyOne);

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
     *
     * @throws java.io.UnsupportedEncodingException
     */
    @org.junit.Test
    public void testGetUrl_ComplexCondition2() throws UnsupportedEncodingException {
        System.out.println("getUrl");
        String table = "people";
        Condition condition1 = new Condition("age", "gte", "14");
        Condition condition2 = new Condition("age", "lte", "18");
        ConditionBundle bundle = new ConditionBundle("or", condition1, condition2);

        boolean onlyOne = false;
        String expResult = "http://domain.com/people?or=(age.gte.14,age.lte.18)";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table, bundle, onlyOne);

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
     *
     * @throws java.io.UnsupportedEncodingException
     */
    @org.junit.Test
    public void testGetUrl_ComplexCondition3() throws UnsupportedEncodingException {
        System.out.println("getUrl");
        String table = "people";
        Condition condition1 = new Condition("grade", "gte", "90");
        Condition condition2 = new Condition("student", "is", "true");
        Condition condition3 = new Condition("age", "gte", "14");
        Condition condition4 = new Condition("age", "is", "null");

        ConditionBundle bundle = new ConditionBundle("and", condition1, condition2);
        ConditionBundle bundle2 = new ConditionBundle("or", condition3, condition4);
        bundle.setConditionBundles(new ArrayList<>());
        bundle.getConditionBundles().add(bundle2);

        boolean onlyOne = false;
        String expResult = "http://domain.com/people?and=(grade.gte.90,student.is.true,or(age.gte.14,age.is.null))";
        String result = UrlBuilder.getUrl(TEST_DATABASE, table, bundle, onlyOne);

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
    public void testGetUrl_Encoding() throws UnsupportedEncodingException {
        System.out.println("Testing getUrl by condition with encoding");
        String table = "table";
        Condition condition = new Condition("title", "eq", "Penguins are majestic");
        boolean onlyOne = false;
        String expResult = "http://domain.com/table?title=eq.Penguins+are+majestic";
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
