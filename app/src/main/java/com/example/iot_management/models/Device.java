//package com.example.iot_management.models;
//
//public class Device {
//    private String type; // Loại thiết bị (Sensor, Light, Fan, etc.)
//    private String name; // Tên thiết bị
//    private String value; // Giá trị của cảm biến (nếu là Sensor)
//    private String status; // Trạng thái (On/Off) nếu là thiết bị điều khiển
//
//    private double temperature;
//
//    private int humidity;
//    private String id;
//    public Device() {}
//    public Device(String id, String name, boolean status, double temperature, int humidity, int value) {
//        this.id = id;
//        this.name = name;
//        this.status = status ? "On" : "Off";
//        this.value = String.valueOf(value);
//        this.type = "Sensor"; // Hoặc bất kỳ giá trị mặc định nào bạn muốn
//        this.temperature = temperature;
//        this.humidity = humidity;
//    }
//    public Device(String type, String name, String value, String status) {
//        this.type = type;
//        this.name = name;
//        this.value = value;
//        this.status = status;
//    }
//
//    public double getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(double temperature) {
//        this.temperature = temperature;
//    }
//
//    public int getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(int humidity) {
//        this.humidity = humidity;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}


package com.example.iot_management.models;

import java.io.Serializable;

public class Device implements Serializable {

    private String id;
    private String type; // Loại thiết bị (dht, led, gas_sensor)
    private boolean state; // Trạng thái của LED (true - bật, false - tắt)
    private double temperature; // Nhiệt độ, chỉ dùng cho DHT
    private double humidity;    // Độ ẩm, chỉ dùng cho DHT
    private int gasLevel;       // Mức gas, chỉ dùng cho Gas Sensor


    // Constructor mặc định không tham số
    public Device() {
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
