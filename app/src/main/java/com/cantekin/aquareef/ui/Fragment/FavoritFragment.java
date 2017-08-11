package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.Data;
import com.cantekin.aquareef.Data.DefaultData;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.GroupDevice.InsertedDeviceListAdapter;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * favori ayarların listelendiği fragment
 */
public class FavoritFragment extends _baseFragment {


    private Map<String, Data> favorites;

    public FavoritFragment() {
        // Required empty public constructor
        setFavorites();
    }

    private void setFavorites() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.favorites));
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
        favorites = new HashMap<>();
        if (fav != null)
            favorites = gson.fromJson(fav, type);
        setListDeleted(favorites, R.id.fvrFavoritLists);

    }

    private void setList(final Map<String, Data> favoriList, int listView) {
        List<String> defaultList = new ArrayList<>();
        for (Map.Entry<String, Data> entry : favoriList.entrySet()) {
            defaultList.add(entry.getKey());
        }
        ListView defaultListView = (ListView) getView().findViewById(listView);
        //   InsertedDeviceListAdapter dfltAdapter = new InsertedDeviceListAdapter(getActivity(), R.layout.row_register_device, defaultList, this);
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

    private void setListDeleted(final Map<String, Data> favoriList, int listView) {
        List<String> defaultList = new ArrayList<>();
        for (Map.Entry<String, Data> entry : favoriList.entrySet()) {
            defaultList.add(entry.getKey());
        }
        ListView defaultListView = (ListView) getView().findViewById(listView);
        FavoritesListAdapter dfltAdapter = new FavoritesListAdapter(getActivity(), R.layout.row_register_device, defaultList, this);
       // ArrayAdapter<String> dfltAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, defaultList);
        defaultListView.setAdapter(dfltAdapter);
        defaultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);
                sendFavorites(favoriList.get(value).stringToSimpleArrayBufferFavorite());
            }
        });
    }

    @Override
    public void deleteItem(String item) {
        favorites.remove(item);
        MyPreference.getPreference(getContext()).setData(MyPreference.FAVORITES, favorites);
        setList(favorites, R.id.fvrFavoritLists);
    }

    public void sendFavorites(byte[] data) {
        Toast.makeText(getContext(), getString(R.string.gonderiliyor), Toast.LENGTH_SHORT).show();
        ((MainActivity) getActivity()).sendDataDevice(data);
    }
}
