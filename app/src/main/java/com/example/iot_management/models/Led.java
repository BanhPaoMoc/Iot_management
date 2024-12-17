package com.example.iot_management.models;

public class Led {
    private String name;
    private boolean state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Led(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

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
