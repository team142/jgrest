/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.api.service;

import com.team142.jgrest.exceptions.JGrestException;
import com.team142.jgrest.framework.api.util.UrlBuilder;
import com.team142.jgrest.framework.nio.HttpClient;
import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;
import com.team142.jgrest.model.MultipleResults;
import com.team142.jgrest.utils.JsonUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author just1689
 * @param <T>
 */
public class TableReader<T> {

    private final Database database;
    private final String table;
    private final Class clazz;

    public TableReader(Database database, String table, Class clazz) {
        this.database = database;
        this.table = table;
        this.clazz = clazz;

    }

    public T getItem(Condition condition) throws JGrestException {
        List<T> results = new ArrayList<>();
        try {
            String url = UrlBuilder.getUrl(database, table, condition, true);
            String json = HttpClient.doGet(database, url);
            List<T> r = (List<T>) JsonUtils.jsonToList(json, clazz);
            results.addAll(r);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);

    }

    public MultipleResults<T> getItems(Condition condition) throws JGrestException {
        MultipleResults<T> results = new MultipleResults<>();
        try {
            String url = UrlBuilder.getUrl(database, table, condition, true);
            String json = HttpClient.doGet(database, url);
            List<T> list = (List<T>) JsonUtils.jsonToList(json, clazz);
            results.getResults().addAll(list);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;

    }

    public MultipleResults<T> getItems(Condition condition, int start, int end) throws JGrestException {
        MultipleResults<T> results = new MultipleResults<>();
        try {
            String url;
            url = UrlBuilder.getUrl(database, table, condition, true);
            String json = HttpClient.doGet(database, url);
            List<T> list = (List<T>) JsonUtils.jsonToList(json, clazz);
            //TODO: show number of rows remaining...
            results.getResults().addAll(list);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TableReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;

    }

}
