package com.example.cnc_driver.net.response;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserRespones {
    String id;
    String username;
    String password;
    int position;

    public UserRespones() {
    }

    public UserRespones(String id, String username, String password, int position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.position = position;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
