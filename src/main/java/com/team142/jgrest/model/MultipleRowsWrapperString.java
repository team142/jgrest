/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
@AllArgsConstructor
public class MultipleRowsWrapperString {

    private String json;
    private boolean moreRows;

}
