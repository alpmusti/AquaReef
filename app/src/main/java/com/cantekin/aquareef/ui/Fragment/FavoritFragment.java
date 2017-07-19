package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cantekin.aquareef.Controller.Data;
import com.cantekin.aquareef.Controller.FavoritesData;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FavoritFragment extends _baseFragment {

    LinearLayout rowLayout;
    private Map<String, Data> defaultFavorites;

    public FavoritFragment() {
        // Required empty public constructor
        setFavorites();
    }

    private void setFavorites() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("Favorites");
        View view = inflater.inflate(R.layout.fragment_favorit, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment();
    }

    private void initFragment() {
        defaultFavorites = new FavoritesData().getFavorites();
        List<String> defaultList = new ArrayList<>();
        for (Map.Entry<String, Data> entry : defaultFavorites.entrySet()) {
            defaultList.add(entry.getKey());
        }
        ListView defaultListView = (ListView) getView().findViewById(R.id.fvrDefaultlists);
        ArrayAdapter<String> dfltAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, defaultList);
        defaultListView.setAdapter(dfltAdapter);
        defaultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);
                sendFavorites(defaultFavorites.get(value).stringToSimpleArrayBufferFavorite());
            }
        });

    }


    public void sendFavorites(byte[] data) {
        ((MainActivity) getActivity()).sendDataDevice(data);
    }
}
