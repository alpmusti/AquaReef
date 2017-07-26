package com.cantekin.aquareef.Data;

import java.util.List;

/**
 * Created by Cantekin on 26.7.2017.
 */

public class Schedule {
    private String name;
    private List<DataSchedule> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataSchedule> getData() {
        return data;
    }

    public void setData(List<DataSchedule> data) {
        this.data = data;
    }
}
