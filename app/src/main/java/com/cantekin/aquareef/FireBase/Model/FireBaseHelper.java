package com.cantekin.aquareef.FireBase.Model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Cantekin on 24.7.2017.
 */

public class FireBaseHelper {
    private DatabaseReference mDatabase;

    public FireBaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void Add(Posts model)
    {
        Log.i("sda","swd");
        String ID=mDatabase.push().getKey();
        Log.i("ID",ID);

        mDatabase.child(ID).setValue(model);
    }

    //mDatabase = FirebaseDatabase.getInstance().getReference();
}
