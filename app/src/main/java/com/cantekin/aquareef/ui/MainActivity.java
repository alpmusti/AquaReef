package com.cantekin.aquareef.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.SendDataToClient;
import com.cantekin.aquareef.ui.Fragment.AquaLinkFragment;
import com.cantekin.aquareef.ui.Fragment.EffectFragment;
import com.cantekin.aquareef.ui.Fragment.FavoritFragment;
import com.cantekin.aquareef.ui.Fragment.ManualFragment;
import com.cantekin.aquareef.ui.Fragment.ScheduleFragment;
import com.cantekin.aquareef.ui.Fragment.SettingsFragment;
import com.cantekin.aquareef.ui.Fragment.ShareFragment;
import com.cantekin.aquareef.ui.Fragment._baseFragment;
import com.cantekin.aquareef.ui.GroupDevice.GroupActivity;
import com.cantekin.aquareef.ui.GroupDevice.GroupFragment;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int RESULT_LOAD_IMAGE = 10001;
    public FragmentTransaction fmTr;
    private SendDataToClient clinetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        replaceFragment(new ManualFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        clinetAdapter = new SendDataToClient(this);

    }

    public void sendDataDevice(String data) {
        clinetAdapter.send(data);
    }

    public void sendDataDevice(byte[] data) {
        clinetAdapter.send(data);
    }

    public void replaceFragment(_baseFragment fragment) {
        if (fmTr == null) {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.add(R.id.fragment_content, fragment);
        } else {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.replace(R.id.fragment_content, fragment);
        }
        fmTr.addToBackStack(null);
        fmTr.commit();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_content);
            if (currentFragment instanceof ManualFragment)
                finish();
            else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, GroupActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.fragment_manual) {
            replaceFragment(new ManualFragment());
        } else if (id == R.id.fragment_schedule) {
            replaceFragment(new ScheduleFragment());
        } else if (id == R.id.fragment_effects) {
            replaceFragment(new EffectFragment());

        } else if (id == R.id.fragment_fav) {
            replaceFragment(new FavoritFragment());

        } else if (id == R.id.fragment_settings) {
            replaceFragment(new SettingsFragment());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", RESULT_LOAD_IMAGE + "");
        Log.i("onActivityResult", requestCode + "");

        // if (requestCode == RESULT_LOAD_IMAGE ) {
        Log.i("onActivityResult", "fds");
        if (data != null) {
            Uri selectedImage = data.getData();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_content);
            ((ShareFragment) currentFragment).setImage(selectedImage);
        }
        //  }
    }

}
