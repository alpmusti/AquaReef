package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;


public class EffectFragment extends _baseFragment {


    public EffectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("Effect");
        View view = inflater.inflate(R.layout.fragment_effect, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    private void initFragment() {
        ImageView imgDayLight = (ImageView) getActivity().findViewById(R.id.imgDayLight);
        imgDayLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEffect(v);
            }
        });

        ImageView imgCloud = (ImageView) getActivity().findViewById(R.id.imgCloud);
        imgCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEffect(v);
            }
        });
        ImageView imgDisco = (ImageView) getActivity().findViewById(R.id.imgDisco);
        imgDisco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEffect(v);
            }
        });
        ImageView imgFlash = (ImageView) getActivity().findViewById(R.id.imgFlash);
        imgFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEffect(v);
            }
        });
    }

    public void sendEffect(View v) {
        Log.i("Image", "sendEffect");
        String data=v.getTag().toString();
        ((MainActivity) getActivity()).sendDataDevice(data);

    }
}
