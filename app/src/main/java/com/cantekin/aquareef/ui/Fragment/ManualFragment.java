package com.cantekin.aquareef.ui.Fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
        setActionBarText(getString(R.string.manual));
        return inflater.inflate(R.layout.fragment_manual, container, false);
    }

    @Override
    protected void initFragment() {
        data = new Data();
        initToggle();
        seekBarRed = (SeekBar) getActivity().findViewById(R.id.seekBarRed);

        seekBarBlue = (SeekBar) getActivity().findViewById(R.id.seekBarBlue);
        setBackGroundColor(seekBarBlue,Color.parseColor("#ff0099cc"));

        seekBarGree = (SeekBar) getActivity().findViewById(R.id.seekBarGreen);
        setBackGroundColor(seekBarGree,Color.parseColor("#ff669900"));

        seekBarLight = (SeekBar) getActivity().findViewById(R.id.seekBarLight);
        setBackGroundColor(seekBarLight,Color.parseColor("#ffffbb33"));

        seekBarRoyal = (SeekBar) getActivity().findViewById(R.id.seekBarRoyal);
        setBackGroundColor(seekBarRoyal,Color.parseColor("#1837fb"));

        seekBarUV = (SeekBar) getActivity().findViewById(R.id.seekBarUV);
        setBackGroundColor(seekBarUV,Color.parseColor("#d446fb"));

        seekBarWhite = (SeekBar) getActivity().findViewById(R.id.seekBarWhite);
        setBackGroundColor(seekBarWhite,Color.parseColor("#ffffffff"));

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

    private void setBackGroundColor(SeekBar seekBar, int Color) {
        LayerDrawable drawable = (LayerDrawable) seekBar.getProgressDrawable();
        Drawable drawableItems = drawable.getDrawable(1);
        ClipDrawable gradientDrawableUnChecked = (ClipDrawable) drawableItems;
        gradientDrawableUnChecked.setColorFilter(Color, PorterDuff.Mode.SRC_OVER);
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
                if (TextUtils.isEmpty(m_Text[0])) {
                    Toast.makeText(getContext(), getString(R.string.lutfen_isim_girin), Toast.LENGTH_SHORT).show();
                    return;
                }
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

    private void setListener(final SeekBar seekBar, int redValue, int toggleId) {
        final TextView valueText = (TextView) getActivity().findViewById(redValue);
        RadioGroup toggle = (RadioGroup) getActivity().findViewById(toggleId);

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (seekBar.getProgress() >= 0 && seekBar.getProgress() <= 100) {//Auto
                    if (checkedId == R.id.redPlus && seekBar.getProgress() > 0) {//-
                        seekBar.setProgress(seekBar.getProgress() - 1);
                        valueText.setText(String.valueOf(seekBar.getProgress()));
                        changeColorListener();
                        group.clearCheck();
                    } else if (checkedId == R.id.minus && seekBar.getProgress() < 100) {//+
                        seekBar.setProgress(seekBar.getProgress() + 1);
                        valueText.setText(String.valueOf(seekBar.getProgress()));
                        changeColorListener();
                        group.clearCheck();
                    }


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
