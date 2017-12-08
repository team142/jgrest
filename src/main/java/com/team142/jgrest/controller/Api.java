package com.team142.jgrest.controller;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.GrestDB;
import com.team142.jgrest.utils.HttpUtils;
import com.team142.jgrest.utils.UrlUtils;
import lombok.Value;

@Value
public class Api {

    private GrestDB database;

    public void insert(String table, Object object) throws Exception {
        String url = UrlUtils.getUrl(database, table);
        HttpUtils.doPostAndForget(url, object);

    }

    public void update(String table, Object object, Condition condition) throws Exception {
        String url = UrlUtils.getUrl(database, table, condition, false);
        HttpUtils.doPatchAndForget(url, object);

    }

    public void delete(String table, Condition condition) throws Exception {
        String url = UrlUtils.getUrl(database, table, condition, false);
        HttpUtils.doDeleteAndForget(url);

    }


    public String getManyByCondition(String table, Condition condition, boolean onlyOne) throws Exception {
        String url = UrlUtils.getUrl(database, table, condition, onlyOne);
        return HttpUtils.doGet(url);

    }

    public String getAll(String table) throws Exception {
        String url = UrlUtils.getUrl(database, table);
        return HttpUtils.doGet(url);
    }

}
