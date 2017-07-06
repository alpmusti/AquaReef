package com.cantekin.aquareef.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cantekin.aquareef.R;


public class DeviceActivity extends AppCompatActivity {
    public FragmentTransaction fmTr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initToolBar();
    }

    public void tik(View v){
     finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
    private void initToolBar() {
        //getSupportActionBar().setTitle("Aquariums");
    }


}
