package com.github.zigcat.ormlite.models;

import com.github.zigcat.ormlite.interfaces.Modelable;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class User implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String fname;

    @DatabaseField
    private String lname;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Role role;

    public User() {
    }

    public User(int id, String fname, String lname, String email, String password, Role role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
