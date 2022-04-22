package com.github.zigcat.ormlite.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class PaginationService {
    public PaginationService(){}

    public <T> List<T> pagination(Dao<T, Integer> dao, long page, long pagesize) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
        queryBuilder.offset((page*pagesize)).limit(pagesize);
        return dao.query(queryBuilder.prepare());
    }
}
