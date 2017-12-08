package com.team142.jgrest.controller;

import com.team142.jgrest.model.Condition;
import com.team142.jgrest.model.GrestDB;
import lombok.Data;

@Data
public class Api {

    private GrestDB database;

    public void insert(String table, Object object) throws Exception {
        //TODO: implement
    }

    public void update(String table, Object object, Condition condition) throws Exception {
        //TODO: implement
    }

    public String getOneByCondition(String table, Condition condition) throws Exception {
        //TODO: implement
    }

    public String getManyByCondition(String table, Condition condition) throws Exception {
        //TODO: implement
    }

    public String getAll(String table) throws Exception {
        //TODO: implement
    }

}
