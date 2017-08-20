package com.cantekin.aquareef.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cantekin.aquareef.AquaLink.Constants;
import com.cantekin.aquareef.AquaLink.UdpUnicast;
import com.cantekin.aquareef.AquaLink.Utils;
import com.cantekin.aquareef.R;

import java.util.List;

/**
 * cihazı networke bağlamak
 * üzere tasarlanımıştır
 */
public class AquaLinkFragment extends _baseFragment {

    private UdpUnicast udp;
    private WifiManager wifiManager;
    private String SSID = "AquaReefLed";
    private TextInputEditText pass;
    private TextView ssid;
    private TextView message;

    ArrayAdapter<String> arrayAdapter;
    private Button btnOk;

    private Handler mNetworkHandler;

    public AquaLinkFragment() {
    }
    @SuppressLint("WifiManagerLeak")
    private void init() {
        wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        Log.i("SSID", ssid);
        if (!SSID.equals(ssid.replace("\"", "")))
            getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("AquaLink");
        return inflater.inflate(R.layout.fragment_aqua_link, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        udp = new UdpUnicast();
        udp.setIp("10.10.100.254");
        udp.open();
        handlerHazila();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        init();
        initFragment();
    }

    Thread prosess;

    private void initFragment() {
        message = (TextView) getActivity().findViewById(R.id.message);
        ssid = (TextView) getActivity().findViewById(R.id.aquaLinkSSID);
        ssid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
                showList();
            }
        });
        pass = (TextInputEditText) getActivity().findViewById(R.id.AquaLinkPassword);
        btnOk = (Button) getActivity().findViewById(R.id.AquaLinkOk);
        btnOk.setEnabled(false);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOk.setEnabled(false);
                prosess = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loginNetwork();
                    }
                });
                prosess.start();
                message.setText("");
            }
        });
    }

    private void handlerHazila() {
        mNetworkHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case Constants.RESPONSE_ERROR:
                        message.setText(R.string.passhata);
                        //   Toast.makeText(getContext(), getString(R.string.passhata), Toast.LENGTH_SHORT).show();
                        btnOk.setEnabled(true);
                        break;
                    case Constants.RESPONSE_OKAY:
                        Toast.makeText(getContext(), getString(R.string.islem_basarili), Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void scanWifi() {
        wifiManager.startScan();
        List<ScanResult> scanResults = wifiManager.getScanResults();
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice);
        Log.i("xd", scanResults.size() + "*-*");
        for (int i = 0; i < scanResults.size(); i++) {
            Log.i("xd", scanResults.get(i).SSID + "");
            arrayAdapter.add(scanResults.get(i).SSID);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void showList() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());

        builderSingle.setNegativeButton(getString(R.string.iptal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setNeutralButton(getString(R.string.yenile), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scanWifi();
                showList();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                ssid.setText(strName);
                btnOk.setEnabled(true);
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    int indexCmd = 0;

    public void loginNetwork() {
        indexCmd = 0;
        udp.setListener(new UdpUnicast.UdpUnicastListener() {
            @Override
            public void onReceived(byte[] data, int length) {
                String response = new String(data, 0, length);
                Log.i("onReceived[send]:", response + "-Index:" + indexCmd);
                if (response.trim().startsWith(Constants.RESPONSE_ERR)) {
                    mNetworkHandler.sendEmptyMessageDelayed(Constants.RESPONSE_ERROR, 10);
                } else {
                    indexCmd++;
                    sendNextCmd();
                }
            }
        });
        //modülleri tara-->0
        udp.send(Constants.CMD_SCAN_MODULES);
    }

    private void sendNextCmd() {
        Log.i("sendNextCmd", "indexCmd:" + indexCmd);
        switch (indexCmd) {
            case 1:
                Log.i("sendNextCmd", "1");
                // cmd moduna al-->1
                udp.send(Constants.CMD_ENTER_CMD_MODE);
                udp.send(Constants.CMD_TEST);
                indexCmd = 2;
                break;
            case 3:
                udp.send(Constants.CMD_VER);
                break;
            case 4:
                Log.i("sendNextCmd", "4");
                //modülü STA moduna geçir-->4
                udp.send(Constants.CMD_STA);
                break;
            case 5:
                Log.i("sendNextCmd", "5");
                //ağın SSID sini gönder--->5
                udp.send(String.format(Constants.CMD_WSSSID, ssid.getText().toString()));
                break;

            case 6:
                Log.i("sendNextCmd", "6");
                //password bilgisini gönder-->6
                List<ScanResult> scanResults = wifiManager.getScanResults();
                String password = Utils.generateWskeyCmd(Utils.scan(scanResults, ssid.getText().toString()), pass.getText().toString());
                udp.send(password);
                break;
            case 7:
                Log.i("sendNextCmd", "7");
                udp.send(Constants.CMD_RESET);
                mNetworkHandler.sendEmptyMessageDelayed(Constants.RESPONSE_OKAY, 10);
                break;
            default:
                break;
        }
    }

}
