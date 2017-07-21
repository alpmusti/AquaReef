package com.cantekin.aquareef.ui.GroupDevice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;

import java.util.List;


public class GroupActivity extends ActionBarActivity {

    public List<GrupDevice> allGroup;
    public List<GrupDevice> activeGroup;
    public FragmentTransaction fmTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initToolBar();
        init();
        replaceFragment(new GroupFragment());
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

    private void init() {

    }

    public void tik(View v) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initToolBar() {
        TextView title = (TextView) findViewById(R.id.deviceTitle);
        title.setText("Aquariums");
    }


    private void updateAllDevice() {
        MyPreference.getPreference(getApplicationContext()).setData(MyPreference.GRUPS, allGroup);
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
        int index = isContainsItem(allGroup, gruop);
        if (index != -1)
            allGroup.remove(index);
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

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.group_fragment_content);
        if (currentFragment instanceof GroupFragment)
            finish();
        else
            super.onBackPressed();

    }

}
