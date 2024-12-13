package com.example.iot_management.models;

public class Led {
    private boolean state;

    public Led(boolean state) {
        this.state = state;
    }

    public Led() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
