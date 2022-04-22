package com.github.zigcat.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.zigcat.ormlite.models.Comment;

import java.io.IOException;

public class CommentSerializer extends StdSerializer<Comment> {
    protected CommentSerializer(Class<Comment> t) {
        super(t);
    }

    protected CommentSerializer(JavaType type) {
        super(type);
    }

    protected CommentSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected CommentSerializer(StdSerializer<?> src) {
        super(src);
    }

    public CommentSerializer(){super(Comment.class);}

    @Override
    public void serialize(Comment comment, JsonGenerator json, SerializerProvider serializerProvider) throws IOException {
        json.writeStartObject();
        json.writeNumberField("id", comment.getId());
        json.writeStringField("text", comment.getText());
        json.writeStringField("creatingDate", comment.getCreatingDate());
        json.writeObjectField("post", comment.getPost());
        json.writeObjectField("user", comment.getUser());
        json.writeEndObject();
    }
}
