package com.example.iot_management.models;

public class Sensor {
    private double humidity;
    private double temperature;

    // Constructor
    public Sensor(double humidity, double temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    // Getters and Setters
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "humidity=" + humidity +
                ", temperature=" + temperature +
                '}';
    }
}

