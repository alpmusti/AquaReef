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
import android.widget.Toast;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.R;

public class DeviceFragment extends _baseGroupFragment {
    GrupDevice groupDevice;
    private static final String ARG_PARAM1 = "Group";

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
        setActionBarText("Akvaryumlarım");
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
        ImageView btnAdd=(ImageView)getActivity().findViewById(R.id.addIP);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDevice();
            }
        });
    }

    public void addDevice() {
        final String[] m_Text = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Akvaryum Ekle");
        builder.setIcon(R.mipmap.cloud);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getContext());
        input.setHint("IP");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);
        builder.setView(layout);

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();

                if (groupDevice.getDevices().contains(m_Text[0])) {
                    Toast.makeText(getContext(), "Bu isimde bir grup mevcut", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    groupDevice.addDevice(m_Text[0]);
                }
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
}
