package com.cantekin.aquareef.ui.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

/**
 * kaydırma çubukları ile
 * cihadaki ışıkların kontrol edildiği fragment
 */
public class ManualFragment extends _baseFragment {
    Data data;
    private SeekBar seekBarBlue;
    private SeekBar seekBarWhite;
    private SeekBar seekBarRed;
    private SeekBar seekBarGree;
    private SeekBar seekBarLight;
    private SeekBar seekBarRoyal;
    private SeekBar seekBarUV;
    private ToggleSwitch toggleSwitchManuel;

    public ManualFragment() {
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
        ImageButton btnFav = (ImageButton) getActivity().findViewById(R.id.btnAddFavorite);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sde", "sdfas");
                addFavorit();
            }
        });
    }

    public void addFavorit() {
        final String[] m_Text = {""};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.favori));
        builder.setIcon(R.mipmap.aqua_favorites);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setPadding(70, 30, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView txt = new TextView(getActivity());
        txt.setText(getString(R.string.favori_kayit));
        layout.addView(txt);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);
        builder.setView(layout);


        builder.setPositiveButton(getString(R.string.tamam), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text[0] = input.getText().toString();
                String fav = MyPreference.getPreference(getContext()).getData(MyPreference.FAVORITES);
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Data>>() {
                }.getType();
                Map<String, Data> favorites = new HashMap<>();
                if (fav != null)
                    favorites = gson.fromJson(fav, type);
                favorites.put(m_Text[0], data);
                MyPreference.getPreference(getContext()).setData(MyPreference.FAVORITES, favorites);
            }
        });
        builder.setNegativeButton(getString(R.string.iptal), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void changeColorListener() {
        toggleSwitchManuel.setCheckedTogglePosition(0);
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
                valueText.setText(String.valueOf(progress));
                changeColorListener();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // valueText.setText(String.valueOf(seekBar.getProgress()));
                // changeColorListener();
            }
        });
    }

    private void initToggle() {
        toggleSwitchManuel = (ToggleSwitch) getActivity().findViewById(R.id.toggle_manual);
        toggleSwitchManuel.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 1) {//Auto
                    ((MainActivity) getActivity()).sendDataDevice("ooooooooooooooo");
                }
            }
        });
    }
}
