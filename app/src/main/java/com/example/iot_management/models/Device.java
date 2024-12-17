
package com.example.iot_management.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Device implements Serializable {

    private String id;
    private String type; // Loại thiết bị (dht, led, gas_sensor)
    private boolean state; // Trạng thái của LED (true - bật, false - tắt)
    private double temperature; // Nhiệt độ, chỉ dùng cho DHT
    private double humidity;    // Độ ẩm, chỉ dùng cho DHT
    private int gasLevel;       // Mức gas, chỉ dùng cho Gas Sensor

    private ArrayList<Device> deviceId;

    public Device() {
    }

    // Constructor mặc định không tham số
    public Device(Object deviceData) {
        // Cần thiết để Firebase có thể tạo đối tượng
    }
    // Constructor cho DHT
    public Device(String id, String type, double temperature, double humidity) {
        this.id = id;
        this.type = type;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // Constructor cho LED
    public Device(String id, String type, boolean state) {
        this.id = id;
        this.type = type;
        this.state = state;
    }

    // Constructor cho Gas Sensor
    public Device(String id, String type, int gasLevel) {
        this.id = id;
        this.type = type;
        this.gasLevel = gasLevel;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isState() {
        return state;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public int getGasLevel() {
        return gasLevel;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
