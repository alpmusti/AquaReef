package com.cantekin.aquareef.ui.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cantekin.aquareef.Controller.Data;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.ColorSetActivity;
import com.cantekin.aquareef.ui.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;


public class ManualFragment extends _baseFragment {
    Data data;
    private SeekBar seekBarBlue;
    private SeekBar seekBarWhite;
    private SeekBar seekBarRed;
    private SeekBar seekBarGree;
    private SeekBar seekBarLight;
    private SeekBar seekBarRoyal;
    private SeekBar seekBarUV;

    public ManualFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setActionBarText("Manual");
        return inflater.inflate(R.layout.fragment_manual, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new Data();
        initFragment();
    }

    private void initFragment() {
        initToggle();
        seekBarRed = (SeekBar) getActivity().findViewById(R.id.seekBarRed);
        seekBarBlue = (SeekBar) getActivity().findViewById(R.id.seekBarBlue);
        seekBarGree = (SeekBar) getActivity().findViewById(R.id.seekBarGreen);
        seekBarLight = (SeekBar) getActivity().findViewById(R.id.seekBarLight);
        seekBarRoyal = (SeekBar) getActivity().findViewById(R.id.seekBarRoyal);
        seekBarUV = (SeekBar) getActivity().findViewById(R.id.seekBarUV);
        seekBarWhite = (SeekBar) getActivity().findViewById(R.id.seekBarWhite);
        setListener(seekBarRed, R.id.redValue, R.id.toggle_red);
        setListener(seekBarBlue, R.id.blueValue, R.id.toggle_blue);
        setListener(seekBarGree, R.id.greenValue, R.id.toggle_green);
        setListener(seekBarLight, R.id.dayLightValue, R.id.toggle_light);
        setListener(seekBarRoyal, R.id.royalBlueValue, R.id.toggle_royal);
        setListener(seekBarUV, R.id.uvValue, R.id.toggle_uv);
        setListener(seekBarWhite, R.id.whiteValue, R.id.toggle_white);
    }

    private void changeColorListener() {
        data.setRed(seekBarRed.getProgress());
        data.setBlue(seekBarBlue.getProgress());
        data.setGreen(seekBarGree.getProgress());
        data.setdWhite(seekBarLight.getProgress());
        data.setRoyalBlue(seekBarRoyal.getProgress());
        data.setUv(seekBarUV.getProgress());
        data.setWhite(seekBarWhite.getProgress());
        ((MainActivity) getActivity()).sendDataDevice(data.stringToSimpleArrayBufferFavorite());
    }

    private void setListener(final SeekBar seekBar, int redValue, int toggle) {
        final TextView valueText = (TextView) getActivity().findViewById(redValue);
        ToggleSwitch toggleSwitch = (ToggleSwitch) getActivity().findViewById(toggle);
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
                    changeColorListener();
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
                changeColorListener();
            }
        });
    }

    private void initToggle() {
        ToggleSwitch toggleSwitchOne = (ToggleSwitch) getActivity().findViewById(R.id.toggle_manual);
        toggleSwitchOne.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 1) {//Auto
                    ((MainActivity) getActivity()).sendDataDevice("ooooooooooooooo");
                }
            }
        });
    }
}
