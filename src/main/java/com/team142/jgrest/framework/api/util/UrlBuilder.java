package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.ConditionBundle;
import com.team142.jgrest.model.Database;
import java.io.UnsupportedEncodingException;

public class UrlBuilder {

    public static String getUrl(Database database, String table) {
        if (database.getUrl().charAt(database.getUrl().length() - 1) == '/') {
            return database.getUrl() + table;
        }
        return database.getUrl() + '/' + table;

    }

    public static String getUrl(Database database, String table, Condition condition, boolean onlyOne) throws UnsupportedEncodingException {

        //Start with base url
        StringBuilder url = new StringBuilder(getUrl(database, table));

        if (condition != null) {
            url
                    .append("?")
                    .append(condition.toQuery(true));
        }

        //Add limit
        if (onlyOne) {
            url.append(null == condition ? "?" : "&");
            url.append("limit=1");
        }
        return url.toString();

    }

    public static String getUrl(Database database, String table, ConditionBundle conditionBundle, boolean onlyOne) throws UnsupportedEncodingException {

        //Start with base url
        StringBuilder url = new StringBuilder(getUrl(database, table));

        if (conditionBundle.getJoiner() == null && conditionBundle.getConditions() != null && !conditionBundle.getConditions().isEmpty()) {
            url.append(conditionBundle.toSimpleQuery());
            return url.toString();
        }

        if (conditionBundle.getJoiner() != null && conditionBundle.getConditions() != null && !conditionBundle.getConditions().isEmpty() ) {
            url.append("?");
            url.append(conditionBundle.toComplexQuery(true));
            

        }



        //TODO: Add limit
        return url.toString();

    }

}
