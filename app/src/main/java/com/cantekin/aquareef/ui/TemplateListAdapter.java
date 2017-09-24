package com.cantekin.aquareef.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.Fragment._baseFragment;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

import java.util.List;

/**
 * Created by Cantekin on 21.7.2017.
 * grupa eklenmiş cihazlar dapteri silmeli
 */

public class TemplateListAdapter extends ArraySwipeAdapter {
    TemplateListActivity activity;

    public TemplateListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects, TemplateListActivity _activity) {
        super(context, resource, objects);
        activity = _activity;
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
        final String ip = (String) getItem(position);
        if (ip != null) {
            TextView txtName = (TextView) v.findViewById(R.id.row_txt_name);
            txtName.setText(ip);
            ImageView btnDelete = (ImageView) v.findViewById(R.id.row_btn_delete);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog));
        builder.setMessage(activity.getString(R.string.emin_misiniz));

        builder.setPositiveButton(activity.getString(R.string.tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.deleteItem(ip);
            }
        });
        builder.setNegativeButton(activity.getString(R.string.iptal), new DialogInterface.OnClickListener() {
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
