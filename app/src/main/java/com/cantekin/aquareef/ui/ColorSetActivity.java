package com.cantekin.aquareef.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.Data.Schedule;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.SendDataToClient;
import com.google.gson.Gson;

import java.util.Calendar;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class ColorSetActivity extends AppCompatActivity {

    private String color;
    private TextView txtTime;
    private Schedule scheduleData;
    private DataSchedule dataSchedule;
    private TextView txtStart;
    private TextView txtUp;
    private TextView txtDown;
    private TextView txtStop;
    private TextView txtLevel;
    private SeekBar seekBar;
    private SendDataToClient clinetAdapter;
    private Switch moon;
    private ToggleSwitch blue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_set);
        color = getIntent().getExtras().getString("color");
        init();
    }

    private void init() {
        txtStart = (TextView) findViewById(R.id.color_txtStart);
        txtUp = (TextView) findViewById(R.id.color_txtUp);
        txtDown = (TextView) findViewById(R.id.color_txtDown);
        txtStop = (TextView) findViewById(R.id.color_txtStop);
        txtLevel = (TextView) findViewById(R.id.color_texLevel);
        seekBar = (SeekBar) findViewById(R.id.color_seek);
        clinetAdapter = new SendDataToClient(this);

        blue = (ToggleSwitch) findViewById(R.id.color_toggle_blue);
        moon = (Switch) findViewById(R.id.color_sw_moon);


        loadData();


        int isMoon;
        if (dataSchedule.getCode() == 'h')
            isMoon = View.GONE;
        else
            isMoon = View.VISIBLE;


        TextView txtTitle = (TextView) findViewById(R.id.color_txtTitle);
        txtTitle.setText(color);
        Button btnCancel = (Button) findViewById(R.id.color_btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnOk = (Button) findViewById(R.id.color_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProp();
                finish();
            }
        });

        Button btnSend = (Button) findViewById(R.id.color_btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProp();
                sendData();
                finish();
            }
        });

        LinearLayout start = (LinearLayout) findViewById(R.id.color_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                showTimeDialog(R.id.color_txtStart);
            }
        });

        LinearLayout up = (LinearLayout) findViewById(R.id.color_up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                showTimeDialog(R.id.color_txtUp);
            }
        });
        up.setVisibility(isMoon);
        LinearLayout down = (LinearLayout) findViewById(R.id.color_down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                showTimeDialog(R.id.color_txtDown);
            }
        });
        down.setVisibility(isMoon);

        LinearLayout stop = (LinearLayout) findViewById(R.id.color_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                showTimeDialog(R.id.color_txtStop);
            }
        });

        setListener(seekBar, R.id.color_texLevel, R.id.color_toggle_level);

        LinearLayout moon = (LinearLayout) findViewById(R.id.color_lyt_moon);
        if (dataSchedule.getCode() == 'h')
            moon.setVisibility(View.VISIBLE);
        else
            moon.setVisibility(View.GONE);


    }

    private void loadData() {
        String data = MyPreference.getPreference(this).getData(MyPreference.ACTIVESCHEDULE);
        Gson gson = new Gson();
        scheduleData = gson.fromJson(data, Schedule.class);

        for (DataSchedule item : scheduleData.getData()) {
            if (item.getName().equals(color))
                dataSchedule = item;
        }
        txtStart.setText(dataSchedule.getStart());
        txtUp.setText(dataSchedule.getUp());
        txtDown.setText(dataSchedule.getDown());
        txtStop.setText(dataSchedule.getStop());
        txtLevel.setText(dataSchedule.getLevel() + "");
        seekBar.setProgress(dataSchedule.getLevel());

        moon.setChecked(dataSchedule.isMoon());
        blue.setCheckedTogglePosition(dataSchedule.getBlue());

    }

    private void sendData() {
        clinetAdapter.send(dataSchedule.getByte());
    }

    private void setProp() {
        dataSchedule.setStart(txtStart.getText().toString());
        dataSchedule.setUp(txtUp.getText().toString());
        dataSchedule.setDown(txtDown.getText().toString());
        dataSchedule.setStop(txtStop.getText().toString());
        dataSchedule.setLevel(Integer.parseInt(txtLevel.getText().toString()));

        dataSchedule.setMoon(moon.isChecked());
        dataSchedule.setBlue(blue.getCheckedTogglePosition());
        MyPreference.getPreference(this).setData(MyPreference.ACTIVESCHEDULE, scheduleData);
    }

    private void showTimeDialog(final int txtId) {

        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        // setText(selectedHour, selectedMinute);
                        txtTime = (TextView) findViewById(txtId);
                        txtTime.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();

    }


    private void setListener(final SeekBar seekBar, int redValue, int toggle) {
        final TextView valueText = (TextView) findViewById(redValue);
        ToggleSwitch toggleSwitch = (ToggleSwitch) findViewById(toggle);
        toggleSwitch.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (seekBar.getProgress() >= 0 && seekBar.getProgress() <= 100) {//Auto
                    if (position == 0 && seekBar.getProgress() > 0) {//-
                        seekBar.setProgress(seekBar.getProgress() - 1);
                    } else if (position == 1 && seekBar.getProgress() < 100) {//+
                        seekBar.setProgress(seekBar.getProgress() + 1);
                    }
                    valueText.setText(String.valueOf(seekBar.getProgress()));
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                valueText.setText(String.valueOf(seekBar.getProgress()));
            }
        });
    }

}
