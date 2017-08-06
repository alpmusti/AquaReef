package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.cantekin.aquareef.ui.MainActivity;

public abstract class _baseFragment extends Fragment {
    public String android_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i("_baseFragment","androidId"+android_id);
    }

    public void setActionBarText(String title) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void deleteItem(String ip) {
    }
}
