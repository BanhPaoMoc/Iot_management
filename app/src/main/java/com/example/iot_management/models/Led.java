package com.example.iot_management.models;

public class Led {
    private String name;
    private String deviceId;
    private boolean state;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Led(String name, String deviceId, boolean state) {
        this.name = name;
        this.deviceId = deviceId;
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
