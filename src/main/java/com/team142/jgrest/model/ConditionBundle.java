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

    public String toSimpleQuery() {
        StringBuilder out = new StringBuilder("?");
        for (int i = 0; i < getConditions().size(); i++) {
            if (i > 0) {
                out.append("&");
            }
            out.append(getConditions().get(i).toQuery(true));
        }
        return out.toString();

    }

    public String toComplexQuery(boolean first) {
        StringBuilder out = new StringBuilder(first ? "?" : "&");

        boolean firstCondition = true;

        if (joiner != null && !joiner.isEmpty()) {
            out
                    .append(joiner)
                    .append("=(");
        }
        
        for (Condition c : conditions) {
            firstCondition = false;
            out.append(c.toQuery(false));
        }
        for (ConditionBundle b : conditionBundles) {
            if (!firstCondition) out.append(",");
            firstCondition = false;
            out.append(b.toComplexQuery(false));
        }

        if (joiner != null && !joiner.isEmpty()) {
            out
                    .append(")");
        }

        return out.toString();

    }

}
