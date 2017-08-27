package com.cantekin.aquareef.ui.GroupDevice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.IDataService;
import com.cantekin.aquareef.network.NetworkDevice;
import com.cantekin.aquareef.network.UdpDataService;
import com.cantekin.aquareef.ui._baseActivity;

import java.util.List;

/**
 * grupların olduğu ana activity
 */
public class GroupActivity extends _baseActivity {

    public List<GrupDevice> allGroup;
    public List<GrupDevice> activeGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        replaceFragment(new GroupFragment());
    }

    @Override
    public void initActivity() {
        super.initActivity();
        final TextView title = (TextView) findViewById(R.id.deviceTitle);
        title.setText(getString(R.string.akvaryumlarim));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tik(v);
            }
        });
    }

    public void replaceFragment(_baseGroupFragment fragment) {
        if (fmTr == null) {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.add(R.id.group_fragment_content, fragment);
        } else {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.replace(R.id.group_fragment_content, fragment);
        }
        fmTr.addToBackStack(null);
        fmTr.commit();
    }


    public void sendStrom(String IP) {
        NetworkDevice d = new NetworkDevice();
        d.setIP(IP);
        d.setPort("8899");
        IDataService dataService = new UdpDataService();
        dataService.send(d, new String("jjjjjjjjjjjjjjj").getBytes());
    }

    public void tik(View v) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.group_fragment_content);
        if (currentFragment instanceof GroupFragment)
            finish();
        else
            super.onBackPressed();

    }

}
