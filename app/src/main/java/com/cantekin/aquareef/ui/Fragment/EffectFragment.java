package com.cantekin.aquareef.ui.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;

/**
 * cihaza varsayılan efectlerin
 * gönderildiği arayüz
 */

public class EffectFragment extends _baseFragment {
    public int sleepTime = 0;

    public EffectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.efektler));
        View view = inflater.inflate(R.layout.fragment_effect, container, false);
        return view;
    }

    @Override
    protected void initFragment() {
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
        String data = v.getTag().toString();
        ((MainActivity) getActivity()).sendDataDevice(data);
        ((MainActivity) getActivity()).progress = ProgressDialog.show(getContext(), getString(R.string.efektler),
                getString(R.string.gonderiliyor), true);
        switch (data) {
            case "iiiiiiiiiiiiiii":
                sleepTime = 15 * 1000;
                break;

            case "jjjjjjjjjjjjjjj":
                sleepTime = 2 * 1000;
                break;

            case "kkkkkkkkkkkkkkk":
                sleepTime = 4 * 1000;
                break;

            case "lllllllllllllll":
                sleepTime = 4 * 1000;
                break;
        }
        new BackgroundTask().execute((Void) null);
    }
    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ((MainActivity) getActivity()).dismissProgressDialog();
        }
    }
}
