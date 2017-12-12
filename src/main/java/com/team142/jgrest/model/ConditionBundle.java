/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.model;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author just1689
 */
@Data
@AllArgsConstructor
public class ConditionBundle {

    private String joiner;
    private ArrayList<Condition> conditions;
    private ArrayList<ConditionBundle> conditionBundles;

    public ConditionBundle(String joiner, Condition... conditionsArr) {
        this.joiner = joiner;
        this.conditions = new ArrayList<>();
        conditions.addAll(Arrays.asList(conditionsArr));

    }

    public String toComplexQuery() {
        StringBuilder result = new StringBuilder();
//        result.append(baseQuery.contains("?") ? "&" : "?");

        result
                .append(joiner)
                .append("=(");

        int conditionsCount = 0;
        for (Condition condition : conditions) {
            conditionsCount++;
            if (conditionsCount > 1) {
                result.append(",");
            }
            result.append(condition.toPieceOfQuery());
        }
        for (ConditionBundle conditionBundle : conditionBundles) {
            conditionsCount++;
            if (conditionsCount > 1) {
                result.append(",");
            }
            result.append(conditionBundle.toComplexQuery());
        }

        result
                .append(")");

        return result.toString();
    }

}
