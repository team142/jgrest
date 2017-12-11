/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.jgrest.framework.api.service;

import com.team142.jgrest.framework.api.util.UrlBuilder;
import com.team142.jgrest.framework.generic.Deleter;
import com.team142.jgrest.framework.generic.Inserter;
import com.team142.jgrest.framework.generic.Updater;
import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.Database;

/**
 *
 * @author just1689
 * @param <T>
 */
public class TableWriter<T> {

    private final Database database;
    private final String table;
    private final Class clazz;

    public TableWriter(Database database, String table, Class clazz) {
        this.database = database;
        this.table = table;
        this.clazz = clazz;
    }

    public void insert(T object) throws Exception {
        String url = UrlBuilder.getUrl(database, table);
        Inserter.sync(database, url, object);
    }

    public void update(Condition condition, T object) throws Exception {
        String url = UrlBuilder.getUrl(database, table);
        Updater.sync(database, url, object);
    }

    public void delete(Condition condition) throws Exception {
        String url = UrlBuilder.getUrl(database, table);
        Deleter.sync(database, url);
    }

}
