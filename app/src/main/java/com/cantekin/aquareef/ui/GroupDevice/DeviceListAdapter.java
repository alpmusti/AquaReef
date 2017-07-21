package com.cantekin.aquareef.ui.GroupDevice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class DeviceListAdapter extends ArrayAdapter<GrupDevice> {
    public DeviceListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<GrupDevice> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_device, null);
        }
        final GrupDevice device = getItem(position);
        if (device != null) {
            TextView txtName = (TextView) v.findViewById(R.id.row_txt_name);
            TextView txtCount = (TextView) v.findViewById(R.id.row_txt_count);
            txtCount.setText(device.getDevices().size() + " lamb");
            txtName.setText(device.getName());

            ImageButton btnEdit = (ImageButton) v.findViewById(R.id.row_btn_edit);
            ImageButton btnDelete = (ImageButton) v.findViewById(R.id.row_btn_delete);
            if (position == 0) {
                btnEdit.setVisibility(View.INVISIBLE);
                btnDelete.setVisibility(View.INVISIBLE);
            }
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeQuation(device);
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GroupActivity) getContext()).replaceFragment(DeviceFragment.newInstance(device));
                }
            });
            CheckBox checkBox = (CheckBox) v.findViewById(R.id.row_chk_item);
            checkBox.setChecked(((GroupActivity) getContext()).isContainsItem(device) != -1);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        ((GroupActivity) getContext()).addAcitiveGrup(device);
                    else
                        ((GroupActivity) getContext()).removeAcitiveGrup(device);

                }
            });
        }
        return v;
    }


    public void removeQuation(final GrupDevice device) {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Emin misiniz?");

        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((GroupActivity) getContext()).removeGrup(device);

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
