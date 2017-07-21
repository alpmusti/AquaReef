package com.cantekin.aquareef.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Cantekin on 21.7.2017.
 */

public class IpHelper {
    private Context context;

    public IpHelper(Context context) {
        this.context = context;
    }

    public String getIPstart() {
        String IP = getIP();
        if (IP == null)
            return null;
        Log.i("IP", IP);
        String[] chanell = IP.split("\\.");
        return chanell[0] + "." + chanell[1] + "." + chanell[2] + ".";
    }


    public String getIP() {
        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}
