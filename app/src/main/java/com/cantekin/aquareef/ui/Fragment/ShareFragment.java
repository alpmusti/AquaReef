package com.cantekin.aquareef.ui.Fragment;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
        //FireBaseHelper hlper=new FireBaseHelper();
//        Posts ss = new Posts();
//        ss.setUser("sdfsdfds");
//        Log.i("sda", "swd");
//        String ID = mDatabase.push().getKey();
//        Log.i("ID", ID);
//
//        mDatabase.child("posts").child(ID).setValue(ss);
    }

    private void sharedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_shared, null);

        userNick = (TextView) dialogView.findViewById(R.id.aler_txt_usernick);
        userNote = (TextView) dialogView.findViewById(R.id.alert_txt_usernote);
        image = (ImageView) dialogView.findViewById(R.id.alert_img);
        Button send = (Button) dialogView.findViewById(R.id.alert_btn_shared);
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
            }
        });
        builder.setView(dialogView);
        builder.show();
    }

    public void setImage(Uri uri) {
        Log.i("setImage", "fds");
        _uri = uri;
        Picasso.with(getContext()).load(uri).placeholder(R.mipmap.profilebackground).into(image);
        //   image.setImageBitmap(bmp);
    }

    public void upload() {
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        UUID uuid = UUID.randomUUID();

        String randomUUIDString = uuid.toString();
        StorageReference riversRef = mStorageRef.child(randomUUIDString + ".jpeg");

        riversRef.putFile(_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                        Toast.makeText(getContext(), "İşlem Başarısız", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void insertFirebase(String s) {
        Posts ss = getData();
        ss.setDownloadURL(s);
        ss.setLike(0l);
        //ss.setDeviceID(0);
        String ID = mDatabase.push().getKey();
        Log.i("ID", ID);
        mDatabase.child("posts").child(ID).setValue(ss);
        Toast.makeText(getContext(), "İşlem Başarılı", Toast.LENGTH_SHORT).show();

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
        dateAll.setAy(setObjects(findColor(scheduleData.getData(),"Moon")));
        dateAll.setBlue(setObjects(findColor(scheduleData.getData(),"Blue")));
        dateAll.setDwhite(setObjects(findColor(scheduleData.getData(),"D.White")));
        dateAll.setGreen(setObjects(findColor(scheduleData.getData(),"Green")));
        dateAll.setRed(setObjects(findColor(scheduleData.getData(),"Red")));
        dateAll.setRoyalBlue(setObjects(findColor(scheduleData.getData(),"Royal Blue")));
        dateAll.setUv(setObjects(findColor(scheduleData.getData(),"UV")));
        dateAll.setWhite(setObjects(findColor(scheduleData.getData(),"White")));
        d.setAllDate(dateAll);
        return d;
    }

    private DataSchedule findColor(List<DataSchedule> list, String Color) {
        for (DataSchedule item : list) {
            if (item.getName().equals(Color))
                return item;
        }
        return new  DataSchedule();
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

