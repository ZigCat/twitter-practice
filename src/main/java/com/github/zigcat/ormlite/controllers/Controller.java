package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.ormlite.exceptions.NotAuthorizedException;
import com.github.zigcat.ormlite.interfaces.Modelable;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.services.PaginationService;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;
import com.j256.ormlite.dao.Dao;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Map;

public class Controller<T extends Modelable> {
    private Class<T> clazz;
    private Service<T> service;
    private PaginationService paginationService;
    private Logger l;
    private Dao<T, Integer> dao;

    public Controller(Class<T> clazz, Dao<T, Integer> dao){
        this.clazz = clazz;
        this.dao = dao;
        this.service = new Service<>(clazz);
        this.paginationService = new PaginationService();
        this.l = LoggerFactory.getLogger(clazz);
    }

    public void getAll(Context ctx, ObjectMapper om){
        l.info("\t!!!GETTING ALL of "+getClazz().getName());
        Map queryMap = ctx.queryParamMap();
        long page;
        if(queryMap.containsKey("page")){
            page = Long.parseLong(ctx.queryParam("page"));
        } else {
            page = 0;
        }
        try {
            l.info("\t!!!GETTING INFO");
            ctx.result(om.writeValueAsString(paginationService.pagination(getDao(), page, 10)));
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }
    }

    public void getById(Context ctx, ObjectMapper om){
        l.info("\t!!!GETTING INFO BY ID of "+getClazz().getName());
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            T t = getService().getById(getDao(), id);
            l.info("\t!!!OBJECT: "+t.toString());
            l.info("\t!!!GETTING BY ID AS USER (id = "+id+")");
            ctx.result(om.writeValueAsString(t));
            ctx.status(200);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }
    }

    public void create(Context ctx, ObjectMapper om){
        l.info("\t!!!CREATING OBJECT of "+getClazz().getName());
        try {
            T t = om.readValue(ctx.body(), getClazz());
            l.info("\t!!!CREATING OBJECT: "+t.toString());
            getDao().create(t);
            ctx.result(om.writeValueAsString(t));
            ctx.status(201);
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            ctx.status(500);
        }
    }

    public void update(Context ctx, ObjectMapper om){
        l.info("\t!!!UPDATING OBJECT of "+getClazz().getName());
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        try {
            T updT = om.readValue(ctx.body(), getClazz());
            for(T t: getService().listAll(getDao())){
                if(t.getId() == updT.getId()){
                    if(Security.auth(login, password).getId() == updT.getId()){
                        l.info("\t!!!UPDATING "+t.toString());
                        getDao().update(updT);
                        ctx.result(om.writeValueAsString(updT));
                        ctx.status(200);
                    }
                }
            }
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            ctx.status(500);
        } catch (NotAuthorizedException e){
            e.printStackTrace();
            l.warn(e.getMessage());
            ctx.status(401);
        }
    }

    public void delete(Context ctx, ObjectMapper om){
        l.info("\t!!!DELETING OBJECT of "+getClazz().getName());
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            if(Security.auth(login, password).getId() == id){
                l.info("\tDELETING OBJECT WITH ID = "+id);
                getDao().deleteById(id);
                ctx.status(200);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500);
        } catch (NotAuthorizedException e){
            e.printStackTrace();
            l.warn(e.getMessage());
            ctx.status(401);
        }
    }

    public Dao<T, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<T, Integer> dao) {
        this.dao = dao;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Service<T> getService() {
        return service;
    }

    public void setService(Service<T> service) {
        this.service = service;
    }

    public PaginationService getPaginationService() {
        return paginationService;
    }

    public void setPaginationService(PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    public Logger getL() {
        return l;
    }

    public void setL(Logger l) {
        this.l = l;
    }
}
