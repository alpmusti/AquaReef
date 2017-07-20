package com.cantekin.aquareef.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantekin on 20.7.2017.
 */

public class GrupDevice {
    private String name;
    private String description;
    private List<String> devices;

    public GrupDevice() {
        devices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public void addDevice(String device) {
        this.devices.add(device);
    }

    public void removeDevice(String device) {
        this.devices.remove(device);
    }
}
