package com.team142.jgrest.utils;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.GrestDB;

public class UrlUtils {

    public static String getUrl(GrestDB database, String table) {
        return database.getUrl() + table;

    }

    public static String getUrl(GrestDB database, String table, Condition condition, boolean onlyOne) {

        //Start with base url
        StringBuilder url = new StringBuilder(getUrl(database, table));

        //TODO: implement conditions

        //Add limit
        if (onlyOne) {
            url.append(condition == null ? "?" : "&");
            url.append("limit=" + 1);

        }
        return url.toString();

    }

}
