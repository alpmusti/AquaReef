package com.cantekin.aquareef.ui.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class ManualFragment extends _baseFragment {
    Data data;
    private SeekBar seekBarBlue;
    private SeekBar seekBarWhite;
    private SeekBar seekBarRed;
    private SeekBar seekBarGree;
    private SeekBar seekBarLight;
    private SeekBar seekBarRoyal;
    private SeekBar seekBarUV;

    public ManualFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setActionBarText("Manual");
        return inflater.inflate(R.layout.fragment_manual, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new Data();
        initFragment();
     //   scanSubNet("192.168.0.");
//        try {
//            printReachableHosts();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }


    }

    private void ping(String ip) {


        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + ip);
            int returnVal = p1.waitFor();
            if (returnVal == 0) {
                Log.d("dsd", "Trying: " + ip);
                //  hosts.add(inetAddress.getHostName());
//                NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
//                byte[] mac = network.getHardwareAddress();
//                StringBuilder sb = new StringBuilder();
//                for (int j = 0; j < mac.length; j++) {
//
//                    sb.append(String.format("%02X%s", mac[j],
//                            (j < mac.length - 1) ? "-" : ""));
//                }
//                Log.i("qwq", sb.toString() + "-" + inetAddress.getHostName());
                //  getIpFromArpCache();

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d("dsd", "NullPointerException: " + ip);
        }
    }

    public void printReachableHosts() throws SocketException {
        String ipAddress = "192.168.0.1";
        Log.e("aaIPPP", ipAddress);

        ipAddress = ipAddress.substring(0, ipAddress.lastIndexOf('.')) + ".";
        for (int i = 0; i < 256; i++) {
            final String otherAddress = ipAddress + String.valueOf(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ping(otherAddress.toString());
                }
            }).start();
        }
    }

    private void scanSubNet(final String subnet) {

        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        for (int i = 1; i < 255; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("dsd", "Trying: " + subnet + String.valueOf(finalI));
                    try {
                        InetAddress inetAddress = InetAddress.getByName(subnet + String.valueOf(finalI));
                        if (inetAddress.isReachable(1000)) {
                            NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
                            byte[] mac = network.getHardwareAddress();
                            StringBuilder sb = new StringBuilder();
                            for (int j = 0; j < mac.length; j++) {

                                sb.append(String.format("%02X%s", mac[j],
                                        (j < mac.length - 1) ? "-" : ""));
                            }
                            Log.i("qwq", sb.toString() + "-" + inetAddress.getHostName());
                        } else {
                            Log.e("qwq", inetAddress.getHostName());
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    private void initFragment() {
        initToggle();
        seekBarRed = (SeekBar) getActivity().findViewById(R.id.seekBarRed);
        seekBarBlue = (SeekBar) getActivity().findViewById(R.id.seekBarBlue);
        seekBarGree = (SeekBar) getActivity().findViewById(R.id.seekBarGreen);
        seekBarLight = (SeekBar) getActivity().findViewById(R.id.seekBarLight);
        seekBarRoyal = (SeekBar) getActivity().findViewById(R.id.seekBarRoyal);
        seekBarUV = (SeekBar) getActivity().findViewById(R.id.seekBarUV);
        seekBarWhite = (SeekBar) getActivity().findViewById(R.id.seekBarWhite);
        setListener(seekBarRed, R.id.redValue, R.id.toggle_red);
        setListener(seekBarBlue, R.id.blueValue, R.id.toggle_blue);
        setListener(seekBarGree, R.id.greenValue, R.id.toggle_green);
        setListener(seekBarLight, R.id.dayLightValue, R.id.toggle_light);
        setListener(seekBarRoyal, R.id.royalBlueValue, R.id.toggle_royal);
        setListener(seekBarUV, R.id.uvValue, R.id.toggle_uv);
        setListener(seekBarWhite, R.id.whiteValue, R.id.toggle_white);
        ImageButton btnFav = (ImageButton) getActivity().findViewById(R.id.btnAddFavorite);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sde", "sdfas");
                addFavorit();
            }
        });
    }

    public void addFavorit() {
        final String[] m_Text = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Favori");
        builder.setIcon(R.mipmap.cloud);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView txt = new TextView(getActivity());
        txt.setText("Lütfen favori ayarınıza bir isim verin");
        layout.addView(txt);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);
        builder.setView(layout);


        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();
                String fav = MyPreference.getPreference(getContext()).getData(MyPreference.FAVORITES);
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Data>>() {
                }.getType();
                Map<String, Data> favorites = new HashMap<>();
                if (fav != null)
                    favorites = gson.fromJson(fav, type);
                favorites.put(m_Text[0], data);
                MyPreference.getPreference(getContext()).setData(MyPreference.FAVORITES, favorites);
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void changeColorListener() {
        data.setRed(seekBarRed.getProgress());
        data.setBlue(seekBarBlue.getProgress());
        data.setGreen(seekBarGree.getProgress());
        data.setdWhite(seekBarLight.getProgress());
        data.setRoyalBlue(seekBarRoyal.getProgress());
        data.setUv(seekBarUV.getProgress());
        data.setWhite(seekBarWhite.getProgress());
        ((MainActivity) getActivity()).sendDataDevice(data.stringToSimpleArrayBufferFavorite());
    }

    private void setListener(final SeekBar seekBar, int redValue, int toggle) {
        final TextView valueText = (TextView) getActivity().findViewById(redValue);
        ToggleSwitch toggleSwitch = (ToggleSwitch) getActivity().findViewById(toggle);
        toggleSwitch.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (seekBar.getProgress() >= 0 && seekBar.getProgress() <= 100) {//Auto
                    if (position == 0 && seekBar.getProgress() > 0) {//-
                        seekBar.setProgress(seekBar.getProgress() - 1);
                    } else if (position == 1 && seekBar.getProgress() < 100) {//+
                        seekBar.setProgress(seekBar.getProgress() + 1);
                    }
                    valueText.setText(String.valueOf(seekBar.getProgress()));
                    changeColorListener();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                valueText.setText(String.valueOf(seekBar.getProgress()));
                changeColorListener();
            }
        });
    }

    private void initToggle() {
        ToggleSwitch toggleSwitchOne = (ToggleSwitch) getActivity().findViewById(R.id.toggle_manual);
        toggleSwitchOne.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 1) {//Auto
                    ((MainActivity) getActivity()).sendDataDevice("ooooooooooooooo");
                }
            }
        });
    }
}
