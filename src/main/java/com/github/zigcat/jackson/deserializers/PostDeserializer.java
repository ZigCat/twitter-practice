package com.github.zigcat.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.ormlite.exceptions.RedirectionException;
import com.github.zigcat.ormlite.models.Post;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;

import java.io.IOException;
import java.sql.SQLException;

public class PostDeserializer extends StdDeserializer<Post> {
    protected PostDeserializer(Class<?> vc) {
        super(vc);
    }

    protected PostDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected PostDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public PostDeserializer(){
        super(Post.class);
    }

    @Override
    public Post deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode json = jsonParser.getCodec().readTree(jsonParser);
        Service<User> userService = new Service<>(User.class);
        int id = json.get("id").asInt();
        String text = json.get("text").asText();
        String date = json.get("creatingDate").asText();
        int user = json.get("user").asInt();
        try {
            User u = userService.getById(DatabaseConfiguration.userDao, user);
            if(Security.isValidDate(date)){
                return new Post(id, text, date, u);
            } else {
                return new Post(id, text, u);
            }
        } catch (SQLException e) {
            throw new RedirectionException("SQLException(500)");
        }
    }
}
