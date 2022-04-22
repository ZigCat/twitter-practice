package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Post;

import java.io.IOException;

public class PostSerializer extends StdSerializer<Post> {
    protected PostSerializer(Class<Post> t) {
        super(t);
    }

    protected PostSerializer(JavaType type) {
        super(type);
    }

    protected PostSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected PostSerializer(StdSerializer<?> src) {
        super(src);
    }

    public PostSerializer(){super(Post.class);}

    @Override
    public void serialize(Post post, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", post.getId());
        json.writeStringField("text", post.getText());
        json.writeStringField("creatingDate", post.getCreatingDate());
        json.writeObjectField("user", post.getUser());
        json.writeEndObject();
    }
}
