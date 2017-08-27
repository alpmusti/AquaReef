package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cantekin.aquareef.FireBase.Model.Posts;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * firebasde paylaşına dataların gösterildiği fragment
 */

public class MyShareFragment extends _baseFragment {


    private final DatabaseReference mDatabase;
    private MySharedAdapter sharedAdapter;
    private ListView lst;
    private List<Posts> postList;
    public String android_id;

    public MyShareFragment() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myshared, container, false);
        return view;
    }

    @Override
    protected void initFragment() {
        lst = (ListView) getActivity().findViewById(R.id.lst_shared);
        sharedAdapter = new MySharedAdapter(getActivity(), R.layout.row_myshared, postList, mDatabase);
        lst.setAdapter(sharedAdapter);
        ImageButton btnClose = (ImageButton) getActivity().findViewById(R.id.btnFragmentClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                Log.d("item", dataSnapshot.child("posts").getChildrenCount() + "");
                for (DataSnapshot data : dataSnapshot.child("posts").getChildren()) {
                    Posts d = data.getValue(Posts.class);
                    d.setKey(data.getKey());
                    if (android_id.equals(d.getDeviceID()))
                        postList.add(d);
                }
                sharedAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getSupportActionBar().show();
        clear();
    }

}

