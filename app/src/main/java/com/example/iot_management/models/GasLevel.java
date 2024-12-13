package com.example.iot_management.models;

public class GasLevel {
    private String gas_sensor;

    public GasLevel(String gas_sensor) {
        this.gas_sensor = gas_sensor;
    }

    public GasLevel() {
    }

    public String getGas_sensor() {
        return gas_sensor;
    }

    public void setGas_sensor(String gas_sensor) {
        this.gas_sensor = gas_sensor;
    }
}
