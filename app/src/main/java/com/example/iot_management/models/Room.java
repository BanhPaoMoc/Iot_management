package com.example.iot_management.models;

import java.io.Serializable;
import java.util.HashMap;

public class Room implements Serializable {
    private String id;
    private String name;
    private String userId;
    private HashMap<String, Device> devices;

    // Constructor không tham số
    public Room() {
    }
    public Room(String id, String name, String userId, HashMap<String, Device> devices) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.devices = devices;
    }

    // Getter và Setter
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Device> getDevices() {
        return devices;
    }

    public void setDevices(HashMap<String, Device> devices) {
        this.devices = devices;
    }
}
