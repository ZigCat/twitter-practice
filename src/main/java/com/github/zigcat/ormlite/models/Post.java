package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.interfaces.Modelable;
import com.j256.ormlite.field.DatabaseField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String text;

    @DatabaseField
    private String creatingDate;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");

    public Post() {
    }

    public Post(int id, String text, User user) {
        this.id = id;
        this.text = text;
        this.creatingDate = LocalDate.now().format(dateTimeFormatter);
        this.user = user;
    }

    public Post(int id, String text, String creatingDate, User user) {
        this.id = id;
        this.text = text;
        this.creatingDate = creatingDate;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + creatingDate + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}
