package com.cantekin.aquareef.ui.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ScheduleFragment extends _baseFragment {

    LineChartView chart;
    ToggleSwitch toggleSwitchOne;
    ToggleSwitch toggleSwitchTwo;
    private Schedule scheduleData;
    private TextView txtTitle;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("Schedule");
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSchedule();
        loadChart();
    }

    private void initFragment() {
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
        initToggle();

        loadSchedule();
        loadChart();
    }

    private void loadSchedule() {
        String data = MyPreference.getPreference(getContext()).getData(MyPreference.ACTIVESCHEDULE);
        if (data == null)
            loadDefault();
        else {
            Gson gson = new Gson();
            // loadDefault();
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

    private void sendSchedule() {
//        for (DataSchedule item : scheduleData.getData()) {
//            ((MainActivity) getActivity()).sendDataDevice(item.ToArrayBuffer());
//        }
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


//        List<PointValue> values = new ArrayList<PointValue>();
//        values.add(new PointValue(0, 20));
//        values.add(new PointValue(5, 40));
//        values.add(new PointValue(12, 80));
//        values.add(new PointValue(24, 50));
//
//
//        List<PointValue> values2 = new ArrayList<PointValue>();
//        values2.add(new PointValue(2, 0));
//        values2.add(new PointValue(3, 80));
//        values2.add(new PointValue(18, 80));
//        values2.add(new PointValue(20, 15));
//        values2.add(new PointValue(24, 0));
//
//        //In most cased you can call data model methods in builder-pattern-like manner.
//        Line line = new Line(values).setColor(Color.BLUE).setCubic(false);
//        List<Line> lines = new ArrayList<Line>();
//
//        line.setHasLabels(true);
//        //line.setHasLabelsOnlyForSelected(hasLabelForSelected);
//        line.setHasLines(true);
//        line.setHasPoints(false);
//        //    line.setHasGradientToTransparent(hasGradientToTransparent);
//        lines.add(line);
//
//        Line line2 = new Line(values2).setColor(Color.GREEN).setCubic(false);
//        //line.setShape(ValueShape.);
//
//        line2.setHasLabels(true);
//        line.setHasLabelsOnlyForSelected(true);
//        line2.setHasLines(true);
//        line2.setHasPoints(false);
//
//        lines.add(line2);

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

    private void initToggle() {
        toggleSwitchOne = (ToggleSwitch) getActivity().findViewById(R.id.toggle_schedule_one);
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Kırmızı");
        labels.add("Yeşil");
        labels.add("K.Mavi");
        labels.add("Mavi");
        toggleSwitchOne.setLabels(labels);
        toggleSwitchOne.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                Intent intent = new Intent(getActivity(), ColorSetActivity.class);
                String colorData = "";
                switch (position) {
                    case 0:
                        colorData = DefaultData.colorRed;
                        break;
                    case 1:
                        colorData = DefaultData.colorGreen;
                        break;
                    case 2:
                        colorData = DefaultData.colorRoyal;
                        break;
                    case 3:
                        colorData = DefaultData.colorBlue;
                        break;
                }
                intent.putExtra("color", colorData);
                getActivity().startActivity(intent);
            }
        });

        toggleSwitchTwo = (ToggleSwitch) getActivity().findViewById(R.id.toggle_schedule_two);
        labels = new ArrayList<>();
        labels.add("Beyaz");
        labels.add("Gün Işığı");
        labels.add("UV");
        labels.add("Ay");
        toggleSwitchTwo.setLabels(labels);

        toggleSwitchTwo.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                Intent intent = new Intent(getActivity(), ColorSetActivity.class);
                String colorData = "";
                switch (position) {
                    case 0:
                        colorData = DefaultData.colorWhite;
                        break;
                    case 1:
                        colorData = DefaultData.colorDWhite;
                        break;
                    case 2:
                        colorData = DefaultData.colorUV;
                        break;
                    case 3:
                        colorData = DefaultData.colorMoon;
                        break;
                }
                intent.putExtra("color", colorData);
                getActivity().startActivity(intent);
            }
        });
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 110;
        v.left = 0;
        v.right = 25;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
}
