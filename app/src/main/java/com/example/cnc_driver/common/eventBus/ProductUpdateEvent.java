package com.example.cnc_driver.common.eventBus;

public class ProductUpdateEvent {

    public String action;
    public Object object;

    public ProductUpdateEvent(String action, Object object) {
        this.action = action;
        this.object = object;
    }
}
