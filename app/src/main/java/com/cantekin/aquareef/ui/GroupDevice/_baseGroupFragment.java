package com.cantekin.aquareef.ui.GroupDevice;

import android.widget.TextView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.Fragment._baseFragment;

/**
 * cihazları listeleme fragementleri
 * TODO:bunu optimize daha temiz hale getirmek gerek
 */
public abstract class _baseGroupFragment extends _baseFragment {


    public void setActionBarText(String title) {
        TextView txtTitle = (TextView) getActivity().findViewById(R.id.deviceTitle);
        txtTitle.setText(title);
    }
    public GroupActivity getGroupActivity() {
        return ((GroupActivity) super.getActivity());
    }

    @Override
    public void clear() {
        getGroupActivity().dismissProgressDialog();
        System.gc();
        Runtime.getRuntime().gc();
    }

}
