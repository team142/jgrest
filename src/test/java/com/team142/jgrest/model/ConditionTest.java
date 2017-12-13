/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.model;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author just1689
 */
public class ConditionTest {

    private static final String BASE_QUERY = "http://domain.com/table";

    /**
     * Test of toSimpleQuery method, of class Condition.
     */
    @Test
    public void testToSimpleQuery() {
        System.out.println("Does a simple query serialize to a valid http GET");
        Condition instance = new Condition("id", "eq", "1");
        String expResult = "http://domain.com/table?id=eq.1";
        String result = instance.toSimpleQuery(BASE_QUERY);
        
        if (!result.equals(expResult)) {
            System.out.println("Expected");
            System.out.println(expResult);
            System.out.println("Instead got");
            System.out.println(result);
        }
        assertEquals(expResult, result);
    }


}
