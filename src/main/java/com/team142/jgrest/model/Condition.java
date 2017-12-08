package com.team142.jgrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Condition {

    private String field;
    private String condition;
    private String value;

}
