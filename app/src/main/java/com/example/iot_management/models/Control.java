package com.example.iot_management.models;

public class Control {
    private String led;
    private String value;

    // Constructor
    public Control(String led, String value) {
        this.led = led;
        this.value = value;
    }

    // Getters and Setters
    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Control{" +
                "led='" + led + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
