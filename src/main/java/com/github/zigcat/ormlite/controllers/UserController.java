package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.ormlite.models.Role;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.Map;

public class UserController extends Controller<User> {

    public UserController() {
        super(User.class, DatabaseConfiguration.userDao);
    }

    public void getById(Context ctx, ObjectMapper omAdmin, ObjectMapper om){
        getL().info("\t!!!GETTING USER BY ID");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        int id = Integer.parseInt(ctx.pathParam("id"));
        try {
            User u = getService().getById(getDao(), id);
            User secU = Security.auth(login, password);
            if(secU.getRole().equals(Role.ADMIN)){
                getL().warn("\tGETTING USER'S INFO AS ADMIN");
                getL().info(u.toString());
                ctx.result(omAdmin.writeValueAsString(u));
                ctx.status(200);
            } else if(secU.getId() == u.getId()){
                getL().info("\t!!!GETTING INFO TO THIS USER");
                getL().info(u.toString());
                ctx.result(omAdmin.writeValueAsString(u));
                ctx.status(200);
            } else {
                getL().info("\t!!!GETTING USER'S INFO");
                getL().info(u.toString());
                ctx.result(om.writeValueAsString(u));
                ctx.status(200);
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }
    }


    public void getAll(Context ctx, ObjectMapper om, ObjectMapper omAdmin){
        getL().info("\t!!!GETTING ALL USERS");
        String login = ctx.basicAuthCredentials().getUsername();
        String password = ctx.basicAuthCredentials().getPassword();
        Map queryMap = ctx.queryParamMap();
        long page;
        if(queryMap.containsKey("page")){
            page = Long.parseLong(ctx.queryParam("page"));
        } else {
            page = 0;
        }
        try {
            if(Security.auth(login, password).getRole().equals(Role.ADMIN)){
                getL().warn("\tGETTING INFO AS ADMIN");
                ctx.result(omAdmin.writeValueAsString(getPaginationService().pagination(getDao(), page, 10)));
                ctx.status(200);
            } else {
                getL().info("\t!!!GETTING INFO AS USER");
                ctx.result(om.writeValueAsString(getPaginationService().pagination(getDao(), page, 10)));
                ctx.status(200);
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
        }
    }
}
