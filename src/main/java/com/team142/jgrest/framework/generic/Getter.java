/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.generic;

import com.team142.jgrest.exceptions.JGrestException;
import com.team142.jgrest.model.HttpResponseBundle;
import com.team142.jgrest.model.MultipleResults;
import com.team142.jgrest.framework.nio.HttpClient;
import com.team142.jgrest.model.Database;
import com.team142.jgrest.utils.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author just1689
 */
public class Getter<T> {

    private final Class clazz;
    private final Database database;

    public Getter(Class clazz, Database database) {
        this.clazz = clazz;
        this.database = database;
    }

    public T getOne(String url) throws JGrestException, IOException {
        String json = HttpClient.doGet(database, url);
        List<T> rows = (List<T>) JsonUtils.jsonToList(json, clazz);
        if (rows.isEmpty()) {
            return null;
        }
        return rows.get(0);
    }

    public MultipleResults<T> getMoreThanOne(String url) throws JGrestException, IOException {
        HttpResponseBundle res = HttpClient.doGetForBundle(database, url);
        List<T> rows = (List<T>) JsonUtils.jsonToList(res.getBody(), clazz);
        if (rows.isEmpty()) {
            return null;
        }
        MultipleResults<T> results = new MultipleResults<>(new ArrayList<>(), 0, 0, false);
        results.getResults().addAll(rows);
        return results;
    }

}
