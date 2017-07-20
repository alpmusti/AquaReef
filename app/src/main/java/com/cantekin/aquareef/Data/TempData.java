package com.cantekin.aquareef.Data;

import java.util.List;

/**
 * Created by Cantekin on 20.7.2017.
 */

public class TempData {
    private String name;
    private int type;
    private List<DataSchedule> schedule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataSchedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<DataSchedule> schedule) {
        this.schedule = schedule;
    }
}
