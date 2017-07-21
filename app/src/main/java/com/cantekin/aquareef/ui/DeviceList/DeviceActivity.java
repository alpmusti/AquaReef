package com.cantekin.aquareef.ui.DeviceList;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeviceActivity extends ActionBarActivity {

    private List<GrupDevice> allDevice;
    private List<GrupDevice> activeGroup;
    private ListView lstGrups;
    private DeviceListAdapter groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initToolBar();
        init();
    }

    private void init() {
        loadAllGroups();
        loadActiveGrup();
        loadList();
    }

    private void loadList() {
        lstGrups = (ListView) findViewById(R.id.lst_grups);
        groupAdapter = new DeviceListAdapter(this, R.layout.row_device, allDevice);
        lstGrups.setAdapter(groupAdapter);
    }

    private void loadAllGroups() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getApplicationContext()).getData(MyPreference.GRUPS);

        if (all != null) {
            Gson gson = new Gson();
            allDevice = gson.fromJson(all, type);
        } else
            createDefaultDevice();

        final ImageView addBtn = (ImageView) findViewById(R.id.addDevice);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrup();
            }
        });
    }

    private void createDefaultDevice() {
        allDevice = new ArrayList<>();
        GrupDevice aquarium = new GrupDevice();
        aquarium.setName("Aquarium");
        aquarium.setDescription("Default");
        aquarium.addDevice("10.10.100.254");
        allDevice.add(aquarium);
        updateAllDevice();
    }


    public void tik(View v) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void initToolBar() {
        TextView title = (TextView) findViewById(R.id.deviceTitle);
        title.setText("Aquariums");
    }

    public void addGrup() {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Akvaryum Ekle");
        builder.setIcon(R.mipmap.cloud);

        LinearLayout layout = new LinearLayout(this);
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText input = new EditText(this);
        input.setHint("Ad");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);

        final EditText inputDesc = new EditText(this);
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
                if (isContainsItem(allDevice, device) != -1) {
                    Toast.makeText(getApplicationContext(), "Bu isimde bir grup mevcut", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    allDevice.add(device);
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
        MyPreference.getPreference(getApplicationContext()).setData(MyPreference.GRUPS, allDevice);
        groupAdapter.notifyDataSetChanged();
    }

    public void loadActiveGrup() {
        Type type = new TypeToken<ArrayList<GrupDevice>>() {
        }.getType();
        String all = MyPreference.getPreference(getApplicationContext()).getData(MyPreference.ACTIVEGRUPS);
        Log.i("loadActiveGrup", all);

        if (all != null) {
            Gson gson = new Gson();
            activeGroup = gson.fromJson(all, type);
        } else
            activeGroup = new ArrayList<>();
    }

    public void addAcitiveGrup(GrupDevice gruop) {
        if (isContainsItem(activeGroup, gruop) == -1)
            activeGroup.add(gruop);
        MyPreference.getPreference(getApplicationContext()).setData(MyPreference.ACTIVEGRUPS, activeGroup);

    }

    public void removeAcitiveGrup(GrupDevice gruop) {
        int index = isContainsItem(activeGroup, gruop);
        if (index != -1)
            activeGroup.remove(index);
        MyPreference.getPreference(getApplicationContext()).setData(MyPreference.ACTIVEGRUPS, activeGroup);
    }


    public void removeGrup(GrupDevice gruop) {
        int index = isContainsItem(allDevice, gruop);
        if (index != -1)
            allDevice.remove(index);
        updateAllDevice();
    }

    /*
    listede aktif görünüm yapılsın mı kontrolü
     */
    public int isContainsItem(GrupDevice gruop) {
        return isContainsItem(activeGroup, gruop);
    }

    public int isContainsItem(List<GrupDevice> list, GrupDevice gruop) {
        for (GrupDevice item : list) {
            if (gruop.getName().equals(item.getName()))
                return list.indexOf(item);
        }
        return -1;
    }


}
