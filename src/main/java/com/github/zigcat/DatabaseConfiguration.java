package com.github.zigcat;

import com.github.zigcat.ormlite.models.Comment;
import com.github.zigcat.ormlite.models.Post;
import com.github.zigcat.ormlite.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\MI\\databases\\twitter.db";
    public static JdbcPooledConnectionSource source;
    public static Dao<User, Integer> userDao;
    public static Dao<Post, Integer> postDao;
    public static Dao<Comment, Integer> commentDao;

    static{
        try {
            source = new JdbcPooledConnectionSource(DB_URL);
            TableUtils.createTableIfNotExists(source, User.class);
            TableUtils.createTableIfNotExists(source, Post.class);
            TableUtils.createTableIfNotExists(source, Comment.class);
            userDao = DaoManager.createDao(DatabaseConfiguration.source, User.class);
            postDao = DaoManager.createDao(DatabaseConfiguration.source, Post.class);
            commentDao = DaoManager.createDao(DatabaseConfiguration.source, Comment.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
