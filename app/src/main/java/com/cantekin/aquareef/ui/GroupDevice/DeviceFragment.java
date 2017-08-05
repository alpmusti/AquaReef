package com.cantekin.aquareef.ui.GroupDevice;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.IpHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceFragment extends _baseGroupFragment {
    GrupDevice groupDevice;
    ListView insertedList;
    ListView networkList;
    private static final String ARG_PARAM1 = "Group";
    private InsertedDeviceListAdapter insertedAdapter;
    final List<String> networkDevices = new ArrayList<>();

    private NetworkDeviceListAdapter netwokAdapter;

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

        insertedList = (ListView) getActivity().findViewById(R.id.devListInserted);
        insertedAdapter = new InsertedDeviceListAdapter(getAct(), R.layout.row_register_device, groupDevice.getDevices(), this);
        insertedList.setAdapter(insertedAdapter);

        networkList = (ListView) getActivity().findViewById(R.id.devListNetwork);
        netwokAdapter = new NetworkDeviceListAdapter(getAct(), R.layout.row_network_device, networkDevices, this);
        networkList.setAdapter(netwokAdapter);


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
    }


    private void getIpFromArpCache() {
        BufferedReader br = null;
        char buffer[] = new char[65000];
        String currentLine;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
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
                            networkDevices.add(ip);
                            netwokAdapter.notifyDataSetChanged();
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


}

