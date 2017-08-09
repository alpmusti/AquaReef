package com.cantekin.aquareef.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Formatter;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.Ping;
import com.cantekin.aquareef.network.SendDataToClient;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        new BackgroundTask().execute((Void) null);
    }

    public void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void initNetwork() {
        //arp güncellensin diye yapıyoruz bu işlemi
        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String IP = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        final String subIP = IP.substring(0, IP.lastIndexOf("."));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 255; i++)
                    Ping.doPing(subIP + "." + i);
            }
        }).start();
    }

    /**
     * müşteri isteği
     */
    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            startApp();
        }

    }
}
