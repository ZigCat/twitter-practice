package com.github.zigcat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.zigcat.jackson.deserializers.CommentDeserializer;
import com.github.zigcat.jackson.deserializers.PostDeserializer;
import com.github.zigcat.jackson.deserializers.UserDeserializer;
import com.github.zigcat.jackson.serializers.CommentSerializer;
import com.github.zigcat.jackson.serializers.PostSerializer;
import com.github.zigcat.jackson.serializers.UserAdminSerializer;
import com.github.zigcat.jackson.serializers.UserSerializer;
import com.github.zigcat.ormlite.controllers.Controller;
import com.github.zigcat.ormlite.controllers.UserController;
import com.github.zigcat.ormlite.models.Comment;
import com.github.zigcat.ormlite.models.Post;
import com.github.zigcat.ormlite.models.User;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class Main {
    public static void main(String[] args){
        UserController userController = new UserController();
        Controller<Post> postController = new Controller<>(Post.class, DatabaseConfiguration.postDao);
        Controller<Comment> commentController = new Controller<>(Comment.class, DatabaseConfiguration.commentDao);
//Common ObjectMapper
        ObjectMapper om = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(User.class, new UserSerializer());
        sm.addSerializer(Post.class, new PostSerializer());
        sm.addSerializer(Comment.class, new CommentSerializer());
        sm.addDeserializer(User.class, new UserDeserializer());
        sm.addDeserializer(Post.class, new PostDeserializer());
        sm.addDeserializer(Comment.class, new CommentDeserializer());
        om.registerModule(sm);
//Admin ObjectMapper
        ObjectMapper omAdmin = new ObjectMapper();
        SimpleModule smAdmin = new SimpleModule();
        smAdmin.addSerializer(User.class, new UserAdminSerializer());
        smAdmin.addDeserializer(User.class, new UserDeserializer());
//creating app
        Javalin app = Javalin.create(JavalinConfig::enableDevLogging);
        app.start(34567);
//User's CRUD
        app.get("user/", ctx -> userController.getAll(ctx, om, omAdmin));
        app.get("user/:id", ctx -> userController.getById(ctx, om, omAdmin));
        app.post("user/", ctx -> userController.create(ctx, om));
        app.patch("user/", ctx -> userController.update(ctx, om));
        app.delete("user/", ctx -> userController.delete(ctx, om));
//Post's CRUD
        app.get("post/", ctx -> postController.getAll(ctx, om));
        app.get("post/:id", ctx -> postController.getById(ctx, om));
        app.post("post/", ctx -> postController.create(ctx, om));
        app.patch("post/", ctx -> postController.update(ctx, om));
        app.delete("post/", ctx -> postController.delete(ctx, om));
//Comment's CRUD
        app.get("comment/", ctx -> commentController.getAll(ctx, om));
        app.get("comment/:id", ctx -> commentController.getById(ctx, om));
        app.post("comment/", ctx -> commentController.create(ctx, om));
        app.patch("comment/", ctx -> commentController.update(ctx, om));
        app.delete("comment/", ctx -> commentController.delete(ctx, om));
    }
}
