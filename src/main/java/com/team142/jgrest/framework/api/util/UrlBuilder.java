package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.framework.api.Settings;
import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.ConditionBundle;
import com.team142.jgrest.model.Database;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlBuilder {

    public static String getUrl(Database database, String table) {
        if (database.getUrl().charAt(database.getUrl().length() - 1) == '/') {
            return database.getUrl() + table;
        }
        return database.getUrl() + '/' + table;

    }

    public static String getUrl(Database database, String table, Condition condition, boolean onlyOne) throws UnsupportedEncodingException {

        //Start with base url
        String url = getUrl(database, table);

        if (condition != null) {
            url += "?";
            url = condition.toSimpleQuery(url);
        }

        //Add limit
        if (onlyOne) {
            url += null == condition ? "?" : "&";
            url += "limit=1";
        }
        if (Settings.DEBUG_ON.get()) {
            Logger.getLogger(UrlBuilder.class.getName()).log(Level.INFO, "Created url: {0}", url);
        }
        return url;

    }

    public static String getUrl(Database database, String table, ConditionBundle conditionBundle, boolean onlyOne) throws UnsupportedEncodingException {

        //Start with base url
        String url = getUrl(database, table) + "?";

        url += conditionBundle.toComplexQuery(true);

        //TODO: Add limit

        if (Settings.DEBUG_ON.get()) {
            Logger.getLogger(UrlBuilder.class.getName()).log(Level.INFO, "Created url: {0}", url);
        }
        return url;

    }

}
