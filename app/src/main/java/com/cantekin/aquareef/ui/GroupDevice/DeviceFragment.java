package com.cantekin.aquareef.ui.GroupDevice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.IpHelper;
import com.cantekin.aquareef.network.Ping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.WIFI_SERVICE;

/**
 * networkte ki ve grup içindeki cihazlarını listesi
 */
public class DeviceFragment extends _baseGroupFragment {
    GrupDevice groupDevice;
    ListView insertedList;
    ListView networkList;
    private static final String ARG_PARAM1 = "Group";
    private InsertedDeviceListAdapter insertedAdapter;
    final List<String> networkDevices = new ArrayList<>();

    private NetworkDeviceListAdapter netwokAdapter;
    private ImageView btnSearch;

    public DeviceFragment() {
        // Required empty public constructor
    }

    public static DeviceFragment newInstance(GrupDevice _groupDevice) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, _groupDevice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.akvaryumlarim));
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        if (getArguments() != null) {
            groupDevice = (GrupDevice) getArguments().getSerializable(ARG_PARAM1);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    private void initFragment() {
        ImageView btnAdd = (ImageView) getActivity().findViewById(R.id.addIP);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDevice();
            }
        });

        btnSearch = (ImageView) getActivity().findViewById(R.id.seaarch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search();
            }
        });

        insertedList = (ListView) getActivity().findViewById(R.id.devListInserted);
        insertedAdapter = new InsertedDeviceListAdapter(getAct(), R.layout.row_register_device, groupDevice.getDevices(), this);
        insertedList.setAdapter(insertedAdapter);

        networkList = (ListView) getActivity().findViewById(R.id.devListNetwork);
        netwokAdapter = new NetworkDeviceListAdapter(getAct(), R.layout.row_network_device, networkDevices, this);
        networkList.setAdapter(netwokAdapter);

        getIpFromArpCache();

    }

    private void search() {
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_refresh);
        rotation.setRepeatCount(Animation.INFINITE);
        btnSearch.startAnimation(rotation);
        new BackgroundTask().execute((Void) null);
    }

    public void updateList() {
        networkDevices.clear();
        getIpFromArpCache();
    }


    public void addDevice() {
        final String[] m_Text = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.akvaryum_ekle));
        builder.setIcon(R.mipmap.cloud);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getContext());
        input.setHint(getString(R.string.ip));
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        IpHelper ipHelper = new IpHelper(getContext());
        input.setText(ipHelper.getIPstart());
        layout.addView(input);
        builder.setView(layout);

        builder.setPositiveButton(getString(R.string.tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();

                if (groupDevice.getDevices().contains(m_Text[0])) {
                    Toast.makeText(getContext(), getString(R.string.bu_isimde_grup_mevcut), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    groupDevice.addDevice(m_Text[0]);
                    updateAllDevice();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.iptal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void deleteItem(String ip) {
        groupDevice.removeDevice(ip);
        updateAllDevice();
    }

    public void updateAllDevice() {
        MyPreference.getPreference(getContext()).setData(MyPreference.GRUPS, getAct().allGroup);
        insertedAdapter.notifyDataSetChanged();
        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVEGRUPS, null);
    }

    private void getIpFromArpCache() {
        BufferedReader br = null;
        char buffer[] = new char[65000];
        String currentLine;
        try {

            br = new BufferedReader(new FileReader(new File("/proc/net/arp")), 8 * 1024);
            br.close();
            Thread.sleep(1000);
            br = new BufferedReader(new FileReader(new File("/proc/net/arp")), 8 * 1024);
            while ((currentLine = br.readLine()) != null) {
                String[] splitted = currentLine.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (!mac.trim().equals("00:00:00:00:00:00") && mac.length() == 17) {
                        {
                            //   Log.d("sde", "getIpFromArpCache() :: " + currentLine);

                            //                          int remove = mac.lastIndexOf(':');
                            //                          mac = mac.substring(0,remove) + mac.substring(remove+1);
                            //   mac = mac.replace(":", "");
                            Log.i("ds", "getIpFromArpCache() :: ip : " + ip + " mac : " + mac);

                            if (filter(mac.substring(0, 8).toUpperCase())) {
                                networkDevices.add(ip);
                                netwokAdapter.notifyDataSetChanged();
                            }
                            //mIpAddressesList.add(new IpAddress(ip, mac));
                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean filter(String mac) {
        Log.d("mac", mac);
        List<String> filter = new ArrayList<>();
        filter.add("F0:FE:6B");
        filter.add("AC:CF:23");
        return filter.contains(mac);
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getContext().getSystemService(WIFI_SERVICE);
            String IP = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            final String subIP = IP.substring(0, IP.lastIndexOf("."));
            for (int i = 1; i < 255; i++) {
                Ping.doPing(subIP + "." + i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            updateList();
            btnSearch.animate().cancel();
        }

    }
}

