package com.cantekin.aquareef.ui.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.DefaultData;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.Data.Schedule;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.AllColorActivity;
import com.cantekin.aquareef.ui.ColorSetActivity;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * gün simülasyonunun gerçekleştiği fragment
 */

public class ScheduleFragment extends _baseFragment {

    LineChartView chart;
    RadioGroup toggleSwitchOne;
    RadioGroup toggleSwitchTwo;
    private Schedule scheduleData;
    private TextView txtTitle;

    public ScheduleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.schedule));
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    protected void initFragment() {
        txtTitle = (TextView) getActivity().findViewById(R.id.schedule_txt_title);
        TextView txtShared = (TextView) getActivity().findViewById(R.id.schedule_txt_shared);
        txtShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(new ShareFragment());
            }
        });

        Button txtAllColor = (Button) getActivity().findViewById(R.id.schedule_btnAll);
        txtAllColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllColorActivity.class));
            }
        });

        Button btnDefaultLoad = (Button) getActivity().findViewById(R.id.btn_default_load);
        btnDefaultLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDefault();
            }
        });


        TextView txtTakeDevice = (TextView) getActivity().findViewById(R.id.schedule_txt_takedevice);
        txtTakeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity) getActivity()).takeDevice();
                }
                catch (Exception e) {
                }
            }
        });
        initToggle();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSchedule();
        loadChart();
    }

    private void loadSchedule() {
        String data = MyPreference.getPreference(getContext()).getData(MyPreference.ACTIVESCHEDULE);
        if (data == null)
            loadDefault();
        else {
            Gson gson = new Gson();
            scheduleData = gson.fromJson(data, Schedule.class);
        }
        txtTitle.setText(scheduleData.getName());
    }

    public void loadDefault() {
        scheduleData = new DefaultData().getSchedule();
        txtTitle.setText(scheduleData.getName());
        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVESCHEDULE, scheduleData);
        loadChart();
    }

    public void updateScheduleFromDevice(byte[] data) {
        scheduleData = new Schedule();
        scheduleData.setName(getString(R.string.cihazdan_gelen));
        List<DataSchedule> dataSchedules = new ArrayList<>();
        Log.i("cihazdan Gelen", "Toplam Uzunluk" + data.length);
        int l = 9;//ay kısmı gelen data boktan
        for (int i = 1; i < data.length; i += 9) {
            byte[] temp = new byte[10];
            if (i + 9 > 80)
                break;
            for (int t = 0; t < 9; t++) {
                temp[t] = data[i + t];

            }
            DataSchedule d = new DataSchedule();
            d.setProperties(temp, i);
            dataSchedules.add(d);
        }
        scheduleData.setData(dataSchedules);
        MyPreference.getPreference(getContext()).setData(MyPreference.ACTIVESCHEDULE, scheduleData);
        txtTitle.setText(scheduleData.getName());
        loadChart();
    }

    private void initToggle() {
        toggleSwitchOne = (RadioGroup) getActivity().findViewById(R.id.toggleGroupOne);
        toggleSwitchOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Intent intent = new Intent(getActivity(), ColorSetActivity.class);
                String colorData = "";
                switch (checkedId) {
                    case R.id.redToggle:
                        colorData = DefaultData.colorRed;

                        break;
                    case R.id.greenToggle:
                        colorData = DefaultData.colorGreen;
                        break;
                    case R.id.royalBToggle:
                        colorData = DefaultData.colorRoyal;
                        break;
                    case R.id.blueToggle:
                        colorData = DefaultData.colorBlue;
                        break;
                }
                if(checkedId>0) {
                    intent.putExtra("color", colorData);
                    getActivity().startActivity(intent);
                    toggleSwitchOne.clearCheck();
                }
            }
        });



        toggleSwitchTwo = (RadioGroup) getActivity().findViewById(R.id.toggleGroupTwo);
        toggleSwitchTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Intent intent = new Intent(getActivity(), ColorSetActivity.class);
                String colorData = "";
                switch (checkedId) {
                    case R.id.whiteToggle:
                        colorData = DefaultData.colorWhite;
                        break;
                    case R.id.dWhiteToggle:
                        colorData = DefaultData.colorDWhite;
                        break;
                    case R.id.UVToggle:
                        colorData = DefaultData.colorUV;
                        break;
                    case R.id.moonToggle:
                        colorData = DefaultData.colorMoon;
                        break;
                }
                if(checkedId>0) {
                    intent.putExtra("color", colorData);
                    getActivity().startActivity(intent);
                    toggleSwitchTwo.clearCheck();
                }
            }
        });

    }

    private void loadChart() {
        chart = (LineChartView) getActivity().findViewById(R.id.chart);
        chart.setZoomEnabled(false);
        List<Line> lines = new ArrayList<>();
        for (DataSchedule item : scheduleData.getData()) {
            if (item.getCode() != 'h') {
                float startTime = Float.parseFloat(item.getStart().substring(0, 2))
                        + Float.parseFloat(item.getStart().substring(3)) / 100;
                float upTime = Float.parseFloat(item.getUp().substring(0, 2))
                        + Float.parseFloat(item.getUp().substring(3)) / 100;
                float downTime = Float.parseFloat(item.getDown().substring(0, 2))
                        + Float.parseFloat(item.getDown().substring(3)) / 100;
                float stopTime = Float.parseFloat(item.getStop().substring(0, 2))
                        + Float.parseFloat(item.getStop().substring(3)) / 100;
                List<PointValue> values = new ArrayList<PointValue>();
                values.add(new PointValue(startTime, 0));
                values.add(new PointValue(upTime, item.getLevel()));
                values.add(new PointValue(downTime, item.getLevel()));
                values.add(new PointValue(stopTime, 0));
                Line line = new Line(values).setColor(Color.parseColor(item.getColor())).setCubic(false);
                line.setHasLabels(true);
                line.setHasLines(true);
                line.setHasPoints(false);
                lines.add(line);
            } else {
                float startTime = Float.parseFloat(item.getStart().substring(0, 2))
                        + Float.parseFloat(item.getStart().substring(3)) / 100;

                float stopTime = Float.parseFloat(item.getStop().substring(0, 2))
                        + Float.parseFloat(item.getStop().substring(3)) / 100;
                List<PointValue> values = new ArrayList<PointValue>();
                values.add(new PointValue(startTime, 0));
                values.add(new PointValue(startTime, item.getLevel()));
                values.add(new PointValue(stopTime, item.getLevel()));
                values.add(new PointValue(stopTime, 0));
                Line line = new Line(values).setColor(Color.parseColor(item.getColor())).setCubic(false);
                line.setHasLabels(true);
                line.setHasLines(true);
                line.setHasPoints(false);
                lines.add(line);
            }
        }



        LineChartData data = new LineChartData();

        //values of the axeY
        List<AxisValue> axisValuesForY = new ArrayList<>();
        List<AxisValue> axisValuesForX = new ArrayList<>();

        for (int i = 0; i <= 100; i += 25) {
            axisValuesForY.add(new AxisValue(i));
        }
        for (int t = 0; t <= 24; t += 4)
            axisValuesForX.add(new AxisValue(t).setLabel(String.format("%02d:00", t)));

        Axis axeX = new Axis(axisValuesForX).setHasLines(true);
        Axis axeY = new Axis(axisValuesForY).setHasLines(true);
        axeX.setTextColor(Color.DKGRAY);
        axeY.setTextColor(Color.DKGRAY);
        axeX.setLineColor(Color.DKGRAY);
        axeY.setLineColor(Color.DKGRAY);
        data.setAxisXBottom(axeX);
        data.setAxisYLeft(axeY);

        data.setLines(lines);


        chart.setLineChartData(data);
        resetViewport();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 110;
        v.left = 0;
        v.right = 25.2f;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
}
