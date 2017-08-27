package com.cantekin.aquareef.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.cantekin.aquareef.network.SendDataToClient;

/**
 * Created by Cantekin on 26.8.2017.
 */

public abstract class _baseActivity extends AppCompatActivity {

    public ProgressDialog progress;
    public SendDataToClient clinetAdapter;
    public FragmentTransaction fmTr;
    public String SSID = "AquaReefLed";


    public _baseActivity() {
        Log.w("_baseActivity", this.getClass().getName() + "New--------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActivity();
    }

    public void initActivity() {
        udpHazirla();
        if (fmTr == null)
            fmTr = getSupportFragmentManager().beginTransaction();

    }

    private void udpHazirla() {
        if (clinetAdapter == null)
            clinetAdapter = new SendDataToClient(this);
        sendDataDevice("!!!!!!!!!!!!!!!");
    }


    @Override
    public void onPause() {
        super.onPause();
        clear();
    }

    public void dismissProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void clear() {
        dismissProgressDialog();
        if (clinetAdapter != null)
            clinetAdapter = null;
//        if (fmTr != null)
//            fmTr = null;
        //   fmTr.
//        super.onDestroy();
//        System.gc();
//        Runtime.getRuntime().gc();
        Log.w("_baseActivity", this.getClass().getName() + "Destroy--------");
    }

    public void exit() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
        System.exit(0);
    }

    public void sendDataDevice(String data) {
        if (TextUtils.isEmpty(data))
            return;
        if (clinetAdapter == null)
            clinetAdapter = new SendDataToClient(this);
        clinetAdapter.send("!!!!!!!!!!!!!!!");
        clinetAdapter.send(data);
    }

    public void sendDataDevice(byte[] data) {
        if (data == null)
            return;
        if (clinetAdapter == null)
            clinetAdapter = new SendDataToClient(this);
        clinetAdapter.send("!!!!!!!!!!!!!!!");
        clinetAdapter.send(data);
    }


}
