package com.cantekin.aquareef.ui.GroupDevice;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.R;

import java.util.List;

/**
 * Created by Cantekin on 21.7.2017.
 */

public class InsertedDeviceListAdapter extends ArrayAdapter<String> {
    DeviceFragment fragment;
    public InsertedDeviceListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, DeviceFragment _fragment) {
        super(context, resource, objects);
        fragment=_fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_register_device, null);
        }
        final String ip = getItem(position);
        if (ip != null) {
            TextView txtName = (TextView) v.findViewById(R.id.row_txt_name);
            txtName.setText(ip);
            ImageButton btnDelete = (ImageButton) v.findViewById(R.id.row_btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeQuation(ip);
                }
            });
        }
        return v;
    }


    public void removeQuation(final String ip) {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Emin misiniz?");

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragment.deleteIP(ip);

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
}
