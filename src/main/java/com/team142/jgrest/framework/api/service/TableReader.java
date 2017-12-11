/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.api.service;

import com.team142.jgrest.framework.api.util.UrlBuilder;
import com.team142.jgrest.framework.nio.HttpClient;
import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;
import com.team142.jgrest.model.MultipleResults;
import com.team142.jgrest.utils.JsonUtils;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.TimeoutException;

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

    public T getItem(Condition condition) throws SocketException, IOException, TimeoutException {
        String url = UrlBuilder.getUrl(database, table, condition, true);
        String json = HttpClient.doGet(database.getDatabasePool(), url);
        T item = (T) JsonUtils.OBJECT_MAPPER.readValue(json, clazz);
        return item;

    }

    public MultipleResults<T> getItems(Condition condition) throws SocketException, IOException, TimeoutException {
        String url = UrlBuilder.getUrl(database, table, condition, true);
        MultipleResults<T> results = new MultipleResults<>();
        String json = HttpClient.doGet(database.getDatabasePool(), url);
        List<T> list = (List<T>) JsonUtils.jsonToList(json, clazz);
        results.getResults().addAll(list);
        return results;

    }

    public MultipleResults<T> getItems(Condition condition, int start, int end) throws SocketException, IOException, TimeoutException {
        String url = UrlBuilder.getUrl(database, table, condition, true);
        MultipleResults<T> results = new MultipleResults<>();
        String json = HttpClient.doGet(database.getDatabasePool(), url);
        List<T> list = (List<T>) JsonUtils.jsonToList(json, clazz);
        //TODO: show number of rows remaining...
        results.getResults().addAll(list);
        return results;

    }

}