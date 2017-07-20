package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.DefaultData;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavoritFragment extends _baseFragment {


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
        Map<String, Data> defaultFavorites = new DefaultData().getFavorites();
        setList(defaultFavorites, R.id.fvrDefaultlists);


        String fav = MyPreference.getPreference(getContext()).getData(MyPreference.FAVORITES);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Data>>() {
        }.getType();
        Map<String, Data> favorites = new HashMap<>();
        if (fav != null)
            favorites = gson.fromJson(fav, type);
        setList(favorites, R.id.fvrFavoritLists);

    }

    private void setList(final Map<String, Data> favoriList, int listView) {
        List<String> defaultList = new ArrayList<>();
        for (Map.Entry<String, Data> entry : favoriList.entrySet()) {
            defaultList.add(entry.getKey());
        }
        ListView defaultListView = (ListView) getView().findViewById(listView);
        ArrayAdapter<String> dfltAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, defaultList);
        defaultListView.setAdapter(dfltAdapter);
        defaultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);
                sendFavorites(favoriList.get(value).stringToSimpleArrayBufferFavorite());
            }
        });
    }


    public void sendFavorites(byte[] data) {
        ((MainActivity) getActivity()).sendDataDevice(data);
    }
}
