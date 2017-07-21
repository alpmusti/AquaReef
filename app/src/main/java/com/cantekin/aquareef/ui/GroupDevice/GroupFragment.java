package com.cantekin.aquareef.ui.GroupDevice;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class GroupFragment extends _baseGroupFragment {

    private ListView lstGrups;
    private DeviceListAdapter groupAdapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("Akvaryumlarım");
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    private void createDefaultDevice() {
        getAct().allGroup = new ArrayList<>();
        GrupDevice aquarium = new GrupDevice();
        aquarium.setName("Aquarium");
        aquarium.setDescription("Default");
        aquarium.addDevice("10.10.100.254");
        getAct().allGroup.add(aquarium);
        updateAllDevice();
    }
    private void initFragment() {
        final ImageView addBtn = (ImageView) getActivity().findViewById(R.id.addDevice);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrup();
            }
        });
        loadAllGroups();
        loadActiveGrup();
        loadList();
    }

    private void loadAllGroups() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getContext()).getData(MyPreference.GRUPS);
        if (all != null) {
            Gson gson = new Gson();
            getAct().allGroup = gson.fromJson(all, type);
        } else
            createDefaultDevice();
    }
    public void loadActiveGrup() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getContext()).getData(MyPreference.ACTIVEGRUPS);
        Log.i("loadActiveGrup", all);

        if (all != null) {
            Gson gson = new Gson();
            getAct().activeGroup = gson.fromJson(all, type);
        } else
            getAct().activeGroup = new ArrayList<>();
    }
    private void loadList() {
        lstGrups = (ListView) getActivity().findViewById(R.id.lst_grups);
        groupAdapter = new DeviceListAdapter(getAct(), R.layout.row_device, getAct().allGroup);
        lstGrups.setAdapter(groupAdapter);
    }

    public void addGrup() {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Akvaryum Ekle");
        builder.setIcon(R.mipmap.cloud);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getContext());
        input.setHint("Ad");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);

        final EditText inputDesc = new EditText(getContext());
        inputDesc.setHint("Açıklama");
        inputDesc.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputDesc);
        builder.setView(layout);

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();
                m_Text[1] = inputDesc.getText().toString();
                GrupDevice device = new GrupDevice();
                device.setName(m_Text[0]);
                device.setDescription(m_Text[1]);
                if (getAct().isContainsItem(getAct().allGroup, device) != -1) {
                    Toast.makeText(getContext(), "Bu isimde bir grup mevcut", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    getAct().allGroup.add(device);
                    updateAllDevice();

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

    private void updateAllDevice() {
        MyPreference.getPreference(getContext()).setData(MyPreference.GRUPS, getAct().allGroup);
        groupAdapter.notifyDataSetChanged();
    }

}
