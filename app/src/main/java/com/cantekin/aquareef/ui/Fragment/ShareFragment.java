package com.cantekin.aquareef.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.cantekin.aquareef.FireBase.Model.FireBaseHelper;
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


public class ShareFragment extends _baseFragment {


    private final DatabaseReference mDatabase;
    private SharedAdapter sharedAdapter;
    private ListView lst;
    private List<Posts> postList;

    public ShareFragment() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText("Templates");
        View view = inflater.inflate(R.layout.fragment_shared, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // this.view = view;
        initFragment();

    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                Log.d("item",dataSnapshot.child("posts").getChildrenCount()+"");

                for (DataSnapshot data : dataSnapshot.child("posts").getChildren()) {
                    postList.add(data.getValue(Posts.class));
                    Log.d("item","****************************");
                }
                sharedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFragment() {
        lst = (ListView) getActivity().findViewById(R.id.lst_shared);
        sharedAdapter = new SharedAdapter(getContext(), R.layout.row_shared, postList);
        lst.setAdapter(sharedAdapter);
        //FireBaseHelper hlper=new FireBaseHelper();
//        Posts ss = new Posts();
//        ss.setUser("sdfsdfds");
//        Log.i("sda", "swd");
//        String ID = mDatabase.push().getKey();
//        Log.i("ID", ID);
//
//        mDatabase.child("posts").child(ID).setValue(ss);
    }

    public void sendEffect(View v) {
        Log.i("Image", "sendEffect");
        String data = v.getTag().toString();
        ((MainActivity) getActivity()).sendDataDevice(data);

    }
}
