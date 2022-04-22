package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.interfaces.Modelable;
import com.j256.ormlite.field.DatabaseField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comment implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String text;

    @DatabaseField
    private String creatingDate;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Post post;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");

    public Comment() {
    }

    public Comment(int id, String text, User user, Post post) {
        this.id = id;
        this.text = text;
        this.creatingDate = LocalDate.now().format(dateTimeFormatter);
        this.user = user;
        this.post = post;
    }

    public Comment(int id, String text, String creatingDate, User user, Post post) {
        this.id = id;
        this.text = text;
        this.creatingDate = creatingDate;
        this.user = user;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", creatingDate='" + creatingDate + '\'' +
                ", user=" + user.getId() +
                ", post=" + post.getId() +
                '}';
    }
}
