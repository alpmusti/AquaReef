package com.cantekin.aquareef.ui.Fragment;

import android.graphics.Color;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.Schedule;

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
 * Created by Cantekin on 26.7.2017.
 * data yaı grafik te gösterilmek üzere
 * gerekli işlemler yapılır
 */

public class ChartHelper {
    private LineChartView chart;

    public ChartHelper(LineChartView chart) {
        this.chart = chart;
    }

    public void loadChart(Schedule scheduleData) {
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
        v.top = 105;
        v.left = 0;
        v.right = 25;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
}
