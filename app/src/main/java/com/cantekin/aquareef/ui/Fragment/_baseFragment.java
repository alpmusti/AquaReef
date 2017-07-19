package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.cantekin.aquareef.ui.MainActivity;

public abstract class _baseFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setActionBarText(String title) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


}
