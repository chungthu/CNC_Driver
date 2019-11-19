package com.example.cnc_driver.common.eventBus;

public class ActionEvent {
    public String action;
    public Object object;

    public ActionEvent(String action, Object object) {
        this.action = action;
        this.object = object;
    }

    public String getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }
}
