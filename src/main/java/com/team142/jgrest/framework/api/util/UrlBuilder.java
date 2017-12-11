package com.team142.jgrest.framework.api.util;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        
        if (url != null) {
            url
                    .append("?")
                    .append(condition.getField())
                    .append("=")
                    .append(condition.getCondition())
                    .append(".")
                    .append(URLEncoder.encode(condition.getValue(), StandardCharsets.UTF_8.toString()));
        }

        //Add limit
        if (onlyOne) {
            url.append(condition == null ? "?" : "&");
            url.append("limit=").append(1);
            
        }
        return url.toString();
        
    }
    
}