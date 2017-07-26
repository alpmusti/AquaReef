package com.cantekin.aquareef.ui.Fragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cantekin.aquareef.FireBase.Model.Posts;
import com.cantekin.aquareef.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Cantekin on 26.7.2017.
 */

public class SharedAdapter extends ArrayAdapter<Posts> {
    public SharedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Posts> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_shared, null);
        }
        Posts object = getItem(position);
        if (object != null) {
            TextView title = (TextView) v.findViewById(R.id.row_shrade_title);
            TextView time = (TextView) v.findViewById(R.id.row_shrade_time);
            TextView desc = (TextView) v.findViewById(R.id.row_shrade_desc);
            TextView count = (TextView) v.findViewById(R.id.row_shrade_counter);
            ImageView image = (ImageView) v.findViewById(R.id.row_shrade_image);
            ImageButton likeBtn = (ImageButton) v.findViewById(R.id.row_shrade_like);
            ImageButton chartBtn = (ImageButton) v.findViewById(R.id.row_shrade_chart);
            title.setText(object.getUser());
            time.setText(object.getSharedDate());
            desc.setText(object.getUserNote());
            count.setText(object.getLike()+"");
            Picasso.with(getContext()).load(object.getDownloadURL()).placeholder(R.mipmap.profilebackground).into(image);
            Log.i("Item", object.getDownloadURL()+"");
        }
        return v;
    }
}
