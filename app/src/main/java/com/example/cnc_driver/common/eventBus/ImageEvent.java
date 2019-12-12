package com.example.cnc_driver.common.eventBus;

public class ImageEvent {
    public String action;
    public String uri;

    public ImageEvent(String action, String uri) {
        this.action = action;
        this.uri = uri;
    }
}
