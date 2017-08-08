package com.cantekin.aquareef.ui.GroupDevice;

import android.os.Bundle;
import android.widget.TextView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.Fragment._baseFragment;

/**
 * cihazları listeleme fragementleri
 * TODO:bunu optimize daha temiz hale getirmek gerek
 */
public abstract class _baseGroupFragment extends _baseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setActionBarText(String title) {
        TextView txtTitle = (TextView) getActivity().findViewById(R.id.deviceTitle);
        txtTitle.setText(title);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public GroupActivity getAct() {
        return ((GroupActivity) super.getActivity());
    }

}
