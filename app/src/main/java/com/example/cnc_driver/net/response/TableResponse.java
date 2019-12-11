package com.example.cnc_driver.net.response;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TableResponse {
    String id;
    String name;
    int status = 0;

    public TableResponse() {
    }

    public TableResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
