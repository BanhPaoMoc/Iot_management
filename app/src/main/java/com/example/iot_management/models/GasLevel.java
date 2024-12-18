package com.example.iot_management.models;

public class GasLevel {
    private int gas_sensor;
    private String deviceId;



    public GasLevel(int gas_sensor, String deviceId) {
        this.gas_sensor = gas_sensor;
        this.deviceId = deviceId;
    }


    public GasLevel() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getGas_sensor() {
        return gas_sensor;
    }

    public void setGas_sensor(int gas_sensor) {
        this.gas_sensor = gas_sensor;
    }
}
