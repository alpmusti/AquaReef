package com.cantekin.aquareef.ui.GroupDevice;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.R;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

/**
 * Created by Cantekin on 5.8.2017.
 * cihaz gruplarının adapteri kaydırmalı silmeli
 */

public class ArraySwipeAdapterSample extends ArraySwipeAdapter {
    GroupFragment fragment;

    public ArraySwipeAdapterSample(Context context, int resource, List<GrupDevice> allGroup, GroupFragment groupFragment) {
        super(context, resource, allGroup);
        fragment=groupFragment;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_device, null);
        }
        final GrupDevice device = (GrupDevice) getItem(position);
        if (device != null) {
            TextView txtName = (TextView) v.findViewById(R.id.row_txt_name);
            TextView txtCount = (TextView) v.findViewById(R.id.row_txt_count);
            txtCount.setText(device.getDevices().size() + " lamb");
            txtName.setText(device.getName());

            ImageButton btnEdit = (ImageButton) v.findViewById(R.id.row_btn_edit);
            ImageView btnDelete = (ImageView) v.findViewById(R.id.row_btn_delete);
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
            final CheckBox checkBox = (CheckBox) v.findViewById(R.id.row_chk_item);
            checkBox.setChecked(((GroupActivity) getContext()).isContainsItem(device) != -1);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        fragment.addAcitiveGrup(device);
                    else
                        fragment.removeAcitiveGrup(device);

                }
            });
            LinearLayout ly=(LinearLayout) v.findViewById(R.id.row_layout);
            ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("dsds","dsadas");
                    boolean isChecked=checkBox.isChecked();
                    Log.d("dsds",isChecked+"");

                    if (!isChecked) {
                        fragment.addAcitiveGrup(device);
                        checkBox.setChecked(true);
                    }
                    else {
                        checkBox.setChecked(false);
                        fragment.removeAcitiveGrup(device);
                    }
                }
            });
        }
        return v;
    }


    public void removeQuation(final GrupDevice device) {
        final String[] m_Text = {"", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(fragment.getString(R.string.emin_misiniz));

        builder.setPositiveButton(fragment.getString(R.string.tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragment.removeGrup(device);

            }
        });
        builder.setNegativeButton(fragment.getString(R.string.iptal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
