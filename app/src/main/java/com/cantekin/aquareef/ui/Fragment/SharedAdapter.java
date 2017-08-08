package com.cantekin.aquareef.ui.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.GrupDevice;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.Data.Schedule;
import com.cantekin.aquareef.FireBase.Model.Posts;
import com.cantekin.aquareef.R;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Cantekin on 26.7.2017.
 * firebasede paylaşılan data için adapter
 */

public class SharedAdapter extends ArrayAdapter<Posts> {
    List<String> myFavorits;
    DatabaseReference mDatabase;

    public SharedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Posts> objects, DatabaseReference _mDatabase) {
        super(context, resource, objects);
        mDatabase = _mDatabase;
        String likes = MyPreference.getPreference(context).getData(MyPreference.LIKES);
        if (likes != null) {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            Gson gson = new Gson();
            myFavorits = gson.fromJson(likes, type);
        } else
            myFavorits = new ArrayList<>();
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
        final Posts object = getItem(position);
        if (object != null) {
            TextView title = (TextView) v.findViewById(R.id.row_shrade_title);
            TextView time = (TextView) v.findViewById(R.id.row_shrade_time);
            TextView desc = (TextView) v.findViewById(R.id.row_shrade_desc);
            final TextView count = (TextView) v.findViewById(R.id.row_shrade_counter);
            ImageView image = (ImageView) v.findViewById(R.id.row_shrade_image);
            final ImageButton likeBtn = (ImageButton) v.findViewById(R.id.row_shrade_like);
            ImageButton chartBtn = (ImageButton) v.findViewById(R.id.row_shrade_chart);
            ImageButton applyBtn = (ImageButton) v.findViewById(R.id.row_shrade_apply);
            title.setText(object.getUser());

            Date date = new Date(Long.parseLong(object.getSharedDate()));
            time.setText(date.toGMTString());
            desc.setText(object.getUserNote());
            count.setText(object.getLike() + "");
            Picasso.with(getContext()).load(object.getDownloadURL()).placeholder(R.drawable.profileback).into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (((ImageView) v).getImageAlpha()== 85)
//                        ((ImageView) v).setImageAlpha(100);
//                    else
//                        ((ImageView) v).setImageAlpha(85);
                }
            });
            if (myFavorits.contains(object.getKey())) {
                likeBtn.setBackgroundResource(R.mipmap.like);
            } else
                likeBtn.setBackgroundResource(R.mipmap.no_like);

            chartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showChart(object);
                    } catch (Exception e) {
                        Log.e("SharedAdapter","not convert data for chart");
                    }
                }
            });

            applyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Schedule sch = convertSchedule(object);
                        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVESCHEDULE, sch);
                        Toast.makeText(getContext(), getContext().getString(R.string.yuklendi), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), getContext().getString(R.string.islem_basarisiz), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myFavorits.contains(object.getKey())) {
                        if (!count.getText().equals("0")) {
                            object.setLike(object.getLike() - 1);
                        }
                        mDatabase.child("posts").child(object.getKey()).setValue(object);
                        myFavorits.remove(object.getKey());
                        likeBtn.setBackgroundResource(R.mipmap.no_like);
                    } else {
                        myFavorits.add(object.getKey());
                        object.setLike(object.getLike() + 1);
                        likeBtn.setBackgroundResource(R.mipmap.like);
                        mDatabase.child("posts").child(object.getKey()).setValue(object);

                    }
                    count.setText(object.getLike() + "");
                    MyPreference.getPreference(getContext()).setData(MyPreference.LIKES, myFavorits);
                }
            });
        }
        return v;
    }

    @NonNull
    private Schedule convertSchedule(Posts object) {
        Schedule sch = new Schedule();
        sch.setName(object.getUser());
        List<DataSchedule> result = new ArrayList<>();
        object.getAllDate().getRed().get(0);
        result.add(setDataItem("Red", 'a', "#ef473a", object.getAllDate().getRed()));
        result.add(setDataItem("Green", 'b', "#33cd5f", object.getAllDate().getGreen()));
        result.add(setDataItem("Royal Blue", 'c', "#11c1f3", object.getAllDate().getRoyalBlue()));
        result.add(setDataItem("Blue", 'd', "#387ef5", object.getAllDate().getBlue()));
        result.add(setDataItem("White", 'e', "#b2b2b2", object.getAllDate().getWhite()));
        result.add(setDataItem("D.White", 'f', "#ffc900", object.getAllDate().getDwhite()));
        result.add(setDataItem("UV", 'g', "#886aea", object.getAllDate().getUv()));
        result.add(setDataItem("Moon", 'h', "#040404", object.getAllDate().getAy()));
        sch.setData(result);
        return sch;
    }

    private void showChart(Posts chartData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_chart, null);
        LineChartView chart = (LineChartView) dialogView.findViewById(R.id.chart);
        TextView title = (TextView) dialogView.findViewById(R.id.schedule_txt_title);
        title.setText(chartData.getUser());
        builder.setView(dialogView);
        builder.show();
        ChartHelper ch = new ChartHelper(chart);
        ch.loadChart(convertSchedule(chartData));
    }

    private DataSchedule setDataItem(String red, char a, String color, List<Object> data) {
        //TODO: sıralama yapmayı unutma, firebasede ki datyaları sıralı değil
        return new DataSchedule(red, a, color, data.get(0).toString(),
                data.get(1).toString(),
                data.get(2).toString(),
                data.get(3).toString(),
                Integer.parseInt(data.get(4).toString()));
    }
}
