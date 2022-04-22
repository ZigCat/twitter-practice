package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.ormlite.exceptions.RedirectionException;
import com.github.zigcat.ormlite.models.Comment;
import com.github.zigcat.ormlite.models.Post;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;

import java.io.IOException;
import java.sql.SQLException;

public class CommentDeserializer extends StdDeserializer<Comment> {
    protected CommentDeserializer(Class<?> vc) {
        super(vc);
    }

    protected CommentDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected CommentDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public CommentDeserializer(){
        super(Comment.class);
    }

    @Override
    public Comment deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        Service<User> userService = new Service<>(User.class);
        Service<Post> postService = new Service<>(Post.class);
        int id = json.get("id").asInt();
        String text = json.get("text").asText();
        String date = json.get("creatingDate").asText();
        int user = json.get("user").asInt();
        int post = json.get("post").asInt();
        try {
            User u = userService.getById(DatabaseConfiguration.userDao, user);
            Post p = postService.getById(DatabaseConfiguration.postDao, post);
            if(Security.isValidDate(date)){
                return new Comment(id, text, date, u, p);
            } else {
                return new Comment(id, text, u, p);
            }
        } catch (SQLException e) {
            throw new RedirectionException("SQLException(500)");
        }
    }
}
