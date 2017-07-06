package com.cantekin.aquareef.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cantekin.aquareef.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startApp();
    }
    public void startApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
