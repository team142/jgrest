/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author just1689
 * @param <T> the type held that is passed back
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleResults<T> {

    private ArrayList<T> results;
    private int start;
    private int end;
    private boolean moreRows;

}
