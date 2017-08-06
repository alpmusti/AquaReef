package com.cantekin.aquareef.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.ui.MainActivity;
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
    }


    private void loadActiveDevices() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(context).getData(MyPreference.ACTIVEGRUPS);
        if (all != null) {
            Log.i("loadActiveDevices", all);
            Gson gson = new Gson();
            activeGroup = gson.fromJson(all, type);
        }
        preperToProp();
    }

    private void preperToProp() {
        devices = new ArrayList<>();
        Log.i("preperToProp", "ss");
        if (activeGroup != null)
            for (GrupDevice group : activeGroup) {
                Log.i("preperToProp active", group.getName());
                Log.i("preperToProp active", "Count" + group.getDevices().size());
                for (String device : group.getDevices()) {
                    NetworkDevice d = new NetworkDevice();
                    d.setIP(device);
                    d.setPort(port);
                    devices.add(d);
                    Log.i("preperToProp devices", device);

                }
            }
    }


    public void send(final String data) {
        Log.i("SendDataToClient", " Message:" + data);
        loadActiveDevices();
        dataService.send(devices, data);
    }

    public void send(final byte[] data) {
        Log.i("SendDataToClient", " Message:" + data);
        //  if (activeGroup == null)
        loadActiveDevices();
        dataService.send(devices, data);
    }

    public void receive() {
        Log.i("receive", " Message:");
        //  if (activeGroup == null)
        loadActiveDevices();
        new BackgroundTask().execute((Void) null);
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {
        byte[] data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            data = dataService.receive(devices);
            Log.e("cihazdan data al", data.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ((MainActivity) context).updateSchedulefromDevice(data);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}
