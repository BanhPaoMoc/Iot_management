package com.example.iot_management.models;

public class GasLevel {
    private int gas_sensor;

    public GasLevel(int gas_sensor) {
        this.gas_sensor = gas_sensor;
    }

    public GasLevel() {
    }

    public int getGas_sensor() {
        return gas_sensor;
    }

    public void setGas_sensor(int gas_sensor) {
        this.gas_sensor = gas_sensor;
    }
}
