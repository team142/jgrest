package com.team142.jgrest.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Condition {

    private String field;
    private String condition;
    private String value;

    public String toSimpleQuery(String baseQuery) {
        StringBuilder result = new StringBuilder(baseQuery);
//        result.append(baseQuery.contains("?") ? "&" : "?");
        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Condition.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        result.append(field).append("=").append(condition).append(".").append(encodedValue);
        return result.toString();

    }

    public String toPieceOfQuery() {
        StringBuilder result = new StringBuilder();
        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Condition.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        result.append(field).append(".").append(condition).append(".").append(encodedValue);
        return result.toString();
    }

}
