package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cantekin.aquareef.ui.MainActivity;

public abstract class _baseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setActionBarText(String title) {
        if (!TextUtils.isEmpty(title))
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateMenuSelect();
        initFragment();
    }

    private void updateMenuSelect() {
        String fragment = this.getClass().getName();
        Log.i("_baseFragment", fragment + "---->>");
        switch (fragment) {
            case "com.cantekin.aquareef.ui.Fragment.ManualFragment":
                changeMenuSelect(0);
                break;
            case "com.cantekin.aquareef.ui.Fragment.ScheduleFragment":
                changeMenuSelect(1);
                break;
            case "com.cantekin.aquareef.ui.Fragment.EffectFragment":
                changeMenuSelect(2);
                break;
            case "com.cantekin.aquareef.ui.Fragment.FavoriteFragment":
                changeMenuSelect(3);
                break;
            case "com.cantekin.aquareef.ui.Fragment.AquaLinkFragment":
                changeMenuSelect(4);
                break;
            case "com.cantekin.aquareef.ui.Fragment.SettingsFragment":
                changeMenuSelect(5);
                break;
        }
    }

    private void changeMenuSelect(int selector) {
        ((MainActivity) getActivity()).navigationView.getMenu().getItem(selector).setChecked(true);
    }

    protected abstract void initFragment();


    @Override
    public void onPause() {
        super.onPause();
        clear();
    }

    public void clear() {
        ((MainActivity) getActivity()).dismissProgressDialog();
//        System.gc();
//        Runtime.getRuntime().gc();
    }

    /**
     * abstrac metot
     *
     * @param ip
     */
    public void deleteItem(String ip) {
    }
}
