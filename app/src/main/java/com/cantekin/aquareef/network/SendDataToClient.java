package com.cantekin.aquareef.network;

import android.content.Context;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cantekin on 22.7.2017.
 */

public class SendDataToClient {
    private Context context;
    public List<GrupDevice> activeGroup;
    List<NetworkDevice> devices;
    IDataService dataService = new UdpDataService();
    private String port = "8899";

    public SendDataToClient(Context context) {
        this.context = context;
        loadActiveDevices();
        preperToProp();
    }


    private void loadActiveDevices() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(context).getData(MyPreference.ACTIVEGRUPS);
        if (all != null) {
            Gson gson = new Gson();
            activeGroup = gson.fromJson(all, type);
        }
    }

    private void preperToProp() {
        devices = new ArrayList<>();
        for (GrupDevice group : activeGroup) {
            for (String device : group.getDevices()) {
                NetworkDevice d = new NetworkDevice();
                d.setIP(device);
                d.setPort(port);
                devices.add(d);
            }
        }
    }


    public void send(final String data) {
        if (activeGroup == null)
            return;
        new Thread(new Runnable() {
            public void run() {
                dataService.send(devices, data);
            }
        }).start();


    }

    public void send(final byte[] data) {
        if (activeGroup == null)
            return;
        new Thread(new Runnable() {
            public void run() {
                dataService.send(devices, data);
            }
        }).start();
    }
}
