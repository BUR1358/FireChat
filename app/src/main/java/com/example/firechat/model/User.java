package com.example.firechat.model;

public class User {
    private String name;
    private String email;
    private String id;
    private int avatarMockUpResourse;

    public User() {
    }

    public User(String name, String email, String id, int avatarMockUpResourse) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.avatarMockUpResourse = avatarMockUpResourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvatarMockUpResourse() {
        return avatarMockUpResourse;
    }

    public void setAvatarMockUpResourse(int avatarMockUpResourse) {
        this.avatarMockUpResourse = avatarMockUpResourse;
    }
}


