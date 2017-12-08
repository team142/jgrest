package com.team142.jgrest.controller;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.GrestDB;
import com.team142.jgrest.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ApiTyped<T> {

    private final String table;
    private final Class clazz;
    private final int limit;
    private final Api api;

    public ApiTyped(GrestDB database, String table, Class clazz, int limit) {
        this.table = table;
        this.clazz = clazz;
        this.limit = limit;
        this.api = new Api(database);

    }

    public void insert(T object) throws Exception {
        api.insert(table, object);

    }

    public void update(T object, Condition condition) throws Exception {
        api.update(table, object, condition);

    }

    public void delete(Condition condition) throws Exception {
        api.delete(table, condition);

    }

    public T getOneByCondition(Condition condition) throws Exception {
        String json = api.getManyByCondition(table, condition, true);
        List<T> rows = (List<T>) JsonUtils.jsonToList(json, clazz);
//        List<T> rows = JsonUtils.OBJECT_MAPPER.readValue(json, JsonUtils.OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        if (rows.isEmpty()) {
            return null;
        }
        return rows.get(0);

    }

    public void getManyByCondition(Condition condition, ArrayList<T> results) throws Exception {
        String json = api.getManyByCondition(table, condition, false);
        List<T> rows = (List<T>) JsonUtils.jsonToList(json, clazz);
//        List<T> rows = JsonUtils.OBJECT_MAPPER.readValue(json, JsonUtils.OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        results.addAll(rows);

    }

    public void getAll(ArrayList<T> results) throws Exception {
        String json = api.getAll(table);
        List<T> rows = (List<T>) JsonUtils.jsonToList(json, clazz);
//        List<T> rows = JsonUtils.OBJECT_MAPPER.readValue(json, JsonUtils.OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        results.addAll(rows);

    }

}
