package com.example.iot_management.models;

public class Dht {
    private int humidity;
    private double temperature;

    public Dht(int humidity, double temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public Dht() {
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
