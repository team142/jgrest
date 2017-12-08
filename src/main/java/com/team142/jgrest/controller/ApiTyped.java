package com.team142.jgrest.controller;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.GrestDB;
import lombok.Value;

import java.util.ArrayList;

@Value
public class ApiTyped<T> {

    private GrestDB database;
    private String table;
    private int limit;

    public void insert(T object) throws Exception {
        //TODO: implement
    }

    public void update(T object, Condition condition) throws Exception {
        //TODO: implement
    }

    public void delete(Condition condition) throws Exception {
        //TODO: implement
    }

    public T getOneByCondition(Condition condition) throws Exception {
        //TODO: implement
    }

    public void getManyByCondition(Condition condition, ArrayList<T> results) throws Exception {
        //TODO: implement
    }

    public void getAll(ArrayList<T> results) throws Exception {
        //TODO: implement
    }


}
