package com.cantekin.aquareef.ui.GroupDevice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
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

/**
 * Cihaz gruplarının listelendiği fragment
 */
public class GroupFragment extends _baseGroupFragment {

    private ListView lstGrups;
    private ArraySwipeAdapterSample groupAdapter;

    public GroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.akvaryumlarim));
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        return view;
    }

    @Override
    protected void initFragment() {
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

    private void createDefaultDevice() {
        getGroupActivity().allGroup = new ArrayList<>();
        GrupDevice aquarium = new GrupDevice();
        aquarium.setName("Aquarium");
        aquarium.setDescription("Default");
        aquarium.addDevice("10.10.100.254");
        getGroupActivity().allGroup.add(aquarium);
        updateAllDevice();
    }

    private void loadAllGroups() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getContext()).getData(MyPreference.GRUPS);
        if (all != null) {
            Gson gson = new Gson();
            getGroupActivity().allGroup = gson.fromJson(all, type);
        } else
            createDefaultDevice();
    }

    public void loadActiveGrup() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getContext()).getData(MyPreference.ACTIVEGRUPS);
        if (all != null) {
            Gson gson = new Gson();
            getGroupActivity().activeGroup = gson.fromJson(all, type);
        } else
            getGroupActivity().activeGroup = new ArrayList<>();
    }

    private void loadList() {
        ArraySwipeAdapterSample sd = new ArraySwipeAdapterSample(getGroupActivity(), R.layout.row_device, getGroupActivity().allGroup, this);
        lstGrups = (ListView) getActivity().findViewById(R.id.lst_grups);
        groupAdapter = new ArraySwipeAdapterSample(getGroupActivity(), R.layout.row_device, getGroupActivity().allGroup, this);

        lstGrups.setAdapter(groupAdapter);
    }

    public void addGrup() {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.akvaryum_ekle));
        builder.setIcon(R.mipmap.aqua_favorites);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(getContext());
        input.setHint(getString(R.string.ad));
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);

        final EditText inputDesc = new EditText(getContext());
        inputDesc.setHint(getString(R.string.acikilama));
        inputDesc.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(inputDesc);
        builder.setView(layout);

        builder.setPositiveButton(getString(R.string.tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();
                m_Text[1] = inputDesc.getText().toString();
                if (TextUtils.isEmpty(m_Text[0])) {
                    Toast.makeText(getContext(), getString(R.string.lutfen_isim_girin), Toast.LENGTH_SHORT).show();
                    return;
                }
                GrupDevice device = new GrupDevice();
                device.setName(m_Text[0]);
                device.setDescription(m_Text[1]);
                if (getGroupActivity().isContainsItem(getGroupActivity().allGroup, device) != -1) {
                    Toast.makeText(getContext(), getString(R.string.bu_isimde_grup_mevcut), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    getGroupActivity().allGroup.add(device);
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

    public void addAcitiveGrup(GrupDevice gruop) {
        if (getGroupActivity().isContainsItem(getGroupActivity().activeGroup, gruop) == -1)
            getGroupActivity().activeGroup.add(gruop);
        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVEGRUPS, getGroupActivity().activeGroup);

    }

    public void removeAcitiveGrup(GrupDevice gruop) {
        int index = getGroupActivity().isContainsItem(getGroupActivity().activeGroup, gruop);
        if (index != -1)
            getGroupActivity().activeGroup.remove(index);
        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVEGRUPS, getGroupActivity().activeGroup);
    }


    public void removeGrup(GrupDevice gruop) {
        int index = getGroupActivity().isContainsItem(getGroupActivity().allGroup, gruop);
        if (index != -1)
            getGroupActivity().allGroup.remove(index);
        updateAllDevice();
    }

    private void updateAllDevice() {
        MyPreference.getPreference(getContext()).setData(MyPreference.GRUPS, getGroupActivity().allGroup);
        if (groupAdapter != null)
            groupAdapter.notifyDataSetChanged();
    }


}
