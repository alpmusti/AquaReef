package com.cantekin.aquareef.ui.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.SendDataToClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * ayarlar fragmenti
 */
public class SettingsFragment extends _baseFragment {
    private ProgressDialog progress;

    LineChartView chart;
    ToggleSwitch toggleSwitchOne;
    int pushTime = 1;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.ayarlar));
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();
    }

    private void initFragment() {
        initToggle();
        Button pushClock = (Button) getActivity().findViewById(R.id.settings_btn_clock);
        pushClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushTimeSend();
            }
        });

        Button timeClock = (Button) getActivity().findViewById(R.id.settings_btn_time);
        timeClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTime();
            }
        });

        TextView txtAquaReefBuy = (TextView) getActivity().findViewById(R.id.settings_txt_buy);
        txtAquaReefBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.akvaryumledmarket.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void sendTime() {
        char code = 'y';
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        int year = cal.get(Calendar.YEAR);
        Log.d("Tarih", d.toString());
        Log.d("Ay", year + "");
        Log.d("Ay", cal.get(Calendar.MONTH) + "");

        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        buffer[1] = 0;
        buffer[2] = (byte) d.getDate();
        buffer[3] = 0;
        buffer[4] = (byte) (d.getMonth() + 1);
        buffer[5] = 0;
        buffer[6] = (byte) (year % 2000);
        Log.d("Ay", buffer[6] + "");
        buffer[7] = 0;
        buffer[8] = (byte) d.getHours();
        buffer[9] = 0;
        buffer[10] = (byte) d.getMinutes();
        buffer[11] = 0;
        buffer[12] = (byte) d.getSeconds();
        buffer[13] = 0;
        buffer[14] = 0;
        send(buffer);
    }

    private void send(byte[] buffer) {
        SendDataToClient clinetAdapter = new SendDataToClient(getContext());
        clinetAdapter.send(buffer);
        progress = ProgressDialog.show(getContext(), getString(R.string.ayarlar),
                getString(R.string.gonderiliyor), true);
        new BackgroundTask().execute((Void) null);

    }

    private void pushTimeSend() {
        char code = 's';
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        buffer[1] = 0;
        buffer[2] = (byte) pushTime;
        buffer[3] = 0;
        buffer[4] = (byte) d.getHours();
        buffer[5] = 0;
        buffer[6] = (byte) d.getMinutes();
        buffer[7] = 0;
        buffer[8] = (byte) d.getSeconds();
        buffer[9] = 0;
        buffer[10] = 0;
        buffer[11] = 0;
        buffer[12] = 0;
        buffer[13] = 0;
        buffer[14] = 0;
        send(buffer);

    }


    private void initToggle() {
        toggleSwitchOne = (ToggleSwitch) getActivity().findViewById(R.id.settings_toggle);
        ArrayList<String> labels = new ArrayList<>();
        labels.add("1 " + getString(R.string.saat_tipi));
        labels.add("2 " + getString(R.string.saat_tipi));
        labels.add("3 " + getString(R.string.saat_tipi));
        labels.add("4 " + getString(R.string.saat_tipi));
        labels.add("5 " + getString(R.string.saat_tipi));
        labels.add("6 " + getString(R.string.saat_tipi));
        labels.add("7 " + getString(R.string.saat_tipi));
        toggleSwitchOne.setLabels(labels);
        toggleSwitchOne.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                pushTime = position + 1;
            }
        });
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        int sleepTime = 1000;

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
            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
}
