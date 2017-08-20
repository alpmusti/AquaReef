package com.cantekin.aquareef.AquaLink;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cantekin.aquareef.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ScanResult> wifiList;

    public ListAdapter(Context context, List<ScanResult> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        System.out.println("viewpos" + position);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row_network_device, null);
        }
        TextView tvDetails = (TextView) view.findViewById(R.id.row_txt_name);
        tvDetails.setText("SSID :: " + wifiList.get(position).SSID);
        return view;
    }


}
