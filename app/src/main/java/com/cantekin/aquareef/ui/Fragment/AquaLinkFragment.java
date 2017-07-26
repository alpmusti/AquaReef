package com.cantekin.aquareef.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.DefaultData;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.ColorSetActivity;
import com.cantekin.aquareef.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class AquaLinkFragment extends _baseFragment {

    private WifiManager wifiManager;
    private String SSID = "NetMASTER Uydunet-7125";
    private String pass = "43c17c0b";


    private String SSIDAP = "AquaReefLed";
    private String passAP = "";

    public AquaLinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("AquaLink");
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    @SuppressLint("WifiManagerLeak")
    private void initFragment() {
        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = SSIDAP;
        wifiConfiguration.preSharedKey = "";
        wifiConfiguration.hiddenSSID = false;
        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedKeyManagement.set(4);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        wifiManager.addNetwork(wifiConfiguration);
        wifiManager.updateNetwork(wifiConfiguration);
        wifiManager.saveConfiguration();
    }
}
