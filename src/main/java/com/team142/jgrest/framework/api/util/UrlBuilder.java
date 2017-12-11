package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;

public class UrlBuilder {

    public static String getUrl(Database database, String table) {
        return database.getUrl() + table;

    }

    public static String getUrl(Database database, String table, Condition condition, boolean onlyOne) {

        //Start with base url
        StringBuilder url = new StringBuilder(getUrl(database, table));

        if (url != null) {
            url.append("?")
                    .append("?")
                    .append(condition.getField())
                    .append("=")
                    .append(condition.getCondition())
                    .append(".")
                    .append(condition.getValue());
        }

        //Add limit
        if (onlyOne) {
            url.append(condition == null ? "?" : "&");
            url.append("limit=").append(1);

        }
        return url.toString();

    }

}
