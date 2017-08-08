package com.cantekin.aquareef.ui.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cantekin.aquareef.Data.DataSchedule;
import com.cantekin.aquareef.Data.MyPreference;
import com.cantekin.aquareef.Data.Schedule;
import com.cantekin.aquareef.FireBase.Model.DateAll;
import com.cantekin.aquareef.FireBase.Model.Posts;
import com.cantekin.aquareef.R;
import com.cantekin.aquareef.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * başka kullanıcıların paylaşımlarının olduğu liste
 */

public class ShareFragment extends _baseFragment {


    private final DatabaseReference mDatabase;
    private SharedAdapter sharedAdapter;
    private ListView lst;
    private List<Posts> postList;
    private ImageView image;
    private Uri _uri;
    private StorageReference mStorageRef;
    private TextView userNick;
    private TextView userNote;

    public ShareFragment() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance().getReference();
        postList = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBarText(getString(R.string.templates));
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
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                Log.d("item", dataSnapshot.child("posts").getChildrenCount() + "");

                for (DataSnapshot data : dataSnapshot.child("posts").getChildren()) {
                    Posts d = data.getValue(Posts.class);
                    d.setKey(data.getKey());
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
    }

    private void initFragment() {
        lst = (ListView) getActivity().findViewById(R.id.lst_shared);
        sharedAdapter = new SharedAdapter(getActivity(), R.layout.row_shared, postList, mDatabase);
        lst.setAdapter(sharedAdapter);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedAlert();
            }
        });

        ImageButton myShared = (ImageButton) getActivity().findViewById(R.id.btnMyShared);
        myShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(new MyShareFragment());
            }
        });

    }

    private void sharedAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_shared, null);

        userNick = (TextView) dialogView.findViewById(R.id.aler_txt_usernick);
        userNote = (TextView) dialogView.findViewById(R.id.alert_txt_usernote);
        image = (ImageView) dialogView.findViewById(R.id.alert_img);
        Button send = (Button) dialogView.findViewById(R.id.alert_btn_shared);

        builder.setView(dialogView);
        final AlertDialog ad = builder.show();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, ((MainActivity) getActivity()).RESULT_LOAD_IMAGE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                ad.dismiss();
            }
        });

    }

    public void setImage(Uri uri) {
        Log.i("setImage", "fds");
        _uri = uri;
        Picasso.with(getContext()).load(uri).placeholder(R.drawable.profileback).into(image);
    }

    public void upload() {
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        StorageReference riversRef = mStorageRef.child(randomUUIDString + ".jpeg");
        UploadTask uploadTask;
        if (_uri == null) {
            Bitmap d = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.profileback);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            uploadTask = riversRef.putBytes(data);
        } else {
            uploadTask = riversRef.putFile(_uri);
        }
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("downloadUrl", downloadUrl.toString());
                insertFirebase(downloadUrl.toString());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getContext(), getString(R.string.islem_basarisiz), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void insertFirebase(String s) {
        Posts ss = getData();
        ss.setDownloadURL(s);
        ss.setLike(0l);
        ss.setDeviceID(android_id);
        String ID = mDatabase.push().getKey();
        Log.i("ID", ID);
        mDatabase.child("posts").child(ID).setValue(ss);
        Toast.makeText(getContext(), getString(R.string.islem_basarili), Toast.LENGTH_SHORT).show();

    }

    public Posts getData() {
        String data = MyPreference.getPreference(getContext()).getData(MyPreference.ACTIVESCHEDULE);
        Gson gson = new Gson();
        Schedule scheduleData = gson.fromJson(data, Schedule.class);
        Posts d = new Posts();
        d.setUser(userNick.getText().toString());
        d.setUserNote(userNote.getText().toString());
        d.setSharedDate(String.valueOf(new Date().getTime()));
        DateAll dateAll = new DateAll();
        dateAll.setAy(setObjects(findColor(scheduleData.getData(), "Moon")));
        dateAll.setBlue(setObjects(findColor(scheduleData.getData(), "Blue")));
        dateAll.setDwhite(setObjects(findColor(scheduleData.getData(), "D.White")));
        dateAll.setGreen(setObjects(findColor(scheduleData.getData(), "Green")));
        dateAll.setRed(setObjects(findColor(scheduleData.getData(), "Red")));
        dateAll.setRoyalBlue(setObjects(findColor(scheduleData.getData(), "Royal Blue")));
        dateAll.setUv(setObjects(findColor(scheduleData.getData(), "UV")));
        dateAll.setWhite(setObjects(findColor(scheduleData.getData(), "White")));
        d.setAllDate(dateAll);
        return d;
    }

    private DataSchedule findColor(List<DataSchedule> list, String Color) {
        for (DataSchedule item : list) {
            if (item.getName().equals(Color))
                return item;
        }
        return new DataSchedule();
    }

    @NonNull
    private List<Object> setObjects(DataSchedule d) {
        List<Object> item = new ArrayList<>();
        item.add(d.getStart());
        item.add(d.getUp());
        item.add(d.getDown());
        item.add(d.getStop());
        item.add(d.getLevel());
        item.add(0);
        item.add(0);
        return item;
    }
}

