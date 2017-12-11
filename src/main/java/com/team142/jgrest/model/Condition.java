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

    public String toQuery(boolean onlyQuery) {

        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Condition.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

        if (onlyQuery) {
            return field + "=" + condition + "." + encodedValue;
        }
        return field + "." + condition + "." + encodedValue;

    }

}
