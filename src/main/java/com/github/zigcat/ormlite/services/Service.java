package com.github.zigcat.ormlite.services;

import com.github.zigcat.ormlite.exceptions.NotFoundException;
import com.github.zigcat.ormlite.interfaces.Modelable;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class Service<T extends Modelable> {
    private Class<T> clazz;

    public Service(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> listAll(Dao<T, Integer> dao) throws SQLException {
        return dao.queryForAll();
    }

    public T getById(Dao<T, Integer> dao, int id) throws SQLException {
        for(T t: listAll(dao)){
            if(t.getId() == id){
                return t;
            }
        }
        throw new NotFoundException("Object with this ID not found ("+clazz.getName()+")");
    }
}
