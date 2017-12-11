/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.model;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author just1689
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseBundle {

    private String body;
    private Map<String, List<String>> headers;

}
