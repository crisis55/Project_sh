package com.example.surreal.shotpingdef.Model;

public class User {
    private String id;
    private String username;
    private String name;
    private String imageurl;

    public User(String id, String username, String name, String imageurl) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imageurl = imageurl;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
