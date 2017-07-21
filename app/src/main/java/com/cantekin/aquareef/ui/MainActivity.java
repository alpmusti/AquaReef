package com.cantekin.aquareef.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.text.format.Formatter;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cantekin.aquareef.R;
import com.cantekin.aquareef.network.NetworkDevice;
import com.cantekin.aquareef.network.UdpDataService;
import com.cantekin.aquareef.ui.DeviceList.DeviceActivity;
import com.cantekin.aquareef.ui.Fragment.EffectFragment;
import com.cantekin.aquareef.ui.Fragment.FavoritFragment;
import com.cantekin.aquareef.ui.Fragment.ManualFragment;
import com.cantekin.aquareef.ui.Fragment.ScheduleFragment;
import com.cantekin.aquareef.ui.Fragment._baseFragment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public FragmentTransaction fmTr;
    private String port = "8899";
    private String IP = "192.168.0.14";

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();

        //    initFragment();
        replaceFragment(new ManualFragment());
        sendDataDevice();

    }

    private void sendDataDevice() {
        Log.i("dsadasd", "ssssssssssssssssssssssssssssss");
        Log.i("dsadasd", IP());
        final String ip = "192.168.0.";
        //getIpFromArpCacheaa();
        ArrayList<String> hosts = scanSubNetq(ip);
        new Thread(new Runnable() {
            public void run() {
                NetworkDevice device = new NetworkDevice();
                device.setIP("10.10.100.254");
                device.setPort("8899");
                String dd = "{00}{00}{00}{00}{0F} {00}{00}{0F} {00}{00}{00}{00}";
                new UdpDataService().send(device, dd);
                // new UdpDataService().send(device, "ooooooooooooooo");
                //   ArrayList<String> hosts = scanSubNet(ip);
            }
        }).start();
    }

    public void sendDataDevice(final String data) {
        Log.i("sendDataDevice", data);
        new Thread(new Runnable() {
            public void run() {
                NetworkDevice device = new NetworkDevice();
                device.setIP(IP);
                device.setPort(port);
                new UdpDataService().send(device, data);
            }

        }).start();
    }

    public void sendDataDevice(final byte[] data) {
        Log.i("sendDataDevice", "byte");
        new Thread(new Runnable() {
            public void run() {
                NetworkDevice device = new NetworkDevice();
                device.setIP(IP);
                device.setPort(port);
                new UdpDataService().send(device, data);
            }

        }).start();
    }

    private void initFragment() {
        if (fmTr == null) {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.add(R.id.fragment_content,new  ManualFragment());
        } else {
            fmTr = getSupportFragmentManager().beginTransaction();
            fmTr.replace(R.id.fragment_content, new  ManualFragment());
        }
        fmTr.addToBackStack(null);
        fmTr.commit();
    }

    private void replaceFragment(_baseFragment fragment) {
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

    private void getIpFromArpCache() {
        BufferedReader br = null;
        char buffer[] = new char[65000];
        String currentLine;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            while ((currentLine = br.readLine()) != null) {
                String[] splitted = currentLine.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (!splitted[3].trim().equals("00:00:00:00:00:00")) {
                        {
                            //   Log.d("sde", "getIpFromArpCache() :: " + currentLine);

                            //                          int remove = mac.lastIndexOf(':');
                            //                          mac = mac.substring(0,remove) + mac.substring(remove+1);
                            //   mac = mac.replace(":", "");
                            Log.i("ds", "getIpFromArpCache() :: ip : " + ip + " mac : " + mac);
                            //mIpAddressesList.add(new IpAddress(ip, mac));
                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<String> scanSubNetqarp(final String subnet) {
        final ArrayList<String> hosts = new ArrayList<String>();

        new Thread(new Runnable() {
            public void run() {
                mac(subnet, hosts);
            }


        }).start();
        return hosts;
    }

    private void mac(String subnet, ArrayList<String> hosts) {

        try {
            Process proc = Runtime.getRuntime().exec("ping -c 1 " + subnet + "0 " + subnet + "255");
            BufferedReader cout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = cout.readLine()) != null) {
                StringTokenizer tkz = new StringTokenizer(line, "\t");
                if (!tkz.hasMoreTokens()) {
                    continue;
                }
                String ip = tkz.nextToken();
                if (!tkz.hasMoreTokens()) {
                    continue;
                }
                String mac = tkz.nextToken();
                if (!tkz.hasMoreTokens()) {
                    continue;
                }
                String vendor = tkz.nextToken();
                Log.d("dsd", "Trying: " + mac);
                Log.d("dsd", "Trying: " + ip);
                Log.d("dsd", "vendor: " + vendor);
                getIpFromArpCache();

//                Victim victim = new Victim(InetAddress.getByName(ip));
//                victim.setMac(mac);
//                victim.setVendor(vendor);
//
//                publishProgress(victim);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> scanSubNetq(final String subnet) {
        final ArrayList<String> hosts = new ArrayList<String>();

        InetAddress inetAddress = null;
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        for (int i = 1; i < 255; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    ping(subnet, hosts, finalI);
                }
            }).start();
        }

        return hosts;
    }

    private void ping(String subnet, ArrayList<String> hosts, int i) {


        try {
            InetAddress inetAddress = InetAddress.getByName(subnet + String.valueOf(i));
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 " + subnet + String.valueOf(i));
            int returnVal = p1.waitFor();
            if (returnVal == 0) {
                Log.d("dsd", "Trying: " + subnet + String.valueOf(i));
                hosts.add(inetAddress.getHostName());
//                NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
//                byte[] mac = network.getHardwareAddress();
//                StringBuilder sb = new StringBuilder();
//                for (int j = 0; j < mac.length; j++) {
//
//                    sb.append(String.format("%02X%s", mac[j],
//                            (j < mac.length - 1) ? "-" : ""));
//                }
//                Log.i("qwq", sb.toString() + "-" + inetAddress.getHostName());
                getIpFromArpCache();

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d("dsd", "NullPointerException: " + subnet + String.valueOf(i));
        }
    }

    private void macc() {
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration ias = ni.getInetAddresses();
                byte[] mac = ni.getHardwareAddress();
                if (mac == null)
                    continue;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {

                    sb.append(String.format("%02X%s", mac[i],
                            (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println(sb.toString());

                while (ias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ias.nextElement();
                    System.out.println(ia.getAddress());

                }

            }
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());

        }
    }

    private ArrayList<String> scanSubNet(String subnet) {
        ArrayList<String> hosts = new ArrayList<String>();

        InetAddress inetAddress = null;
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        for (int i = 1; i < 255; i++) {
            Log.d("dsd", "Trying: " + subnet + String.valueOf(i));
            try {
                inetAddress = InetAddress.getByName(subnet + String.valueOf(i));
                if (inetAddress.isReachable(1000)) {
                    hosts.add(inetAddress.getHostName());
                    NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
                    byte[] mac = network.getHardwareAddress();
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < mac.length; j++) {

                        sb.append(String.format("%02X%s", mac[j],
                                (j < mac.length - 1) ? "-" : ""));
                    }
                    Log.i("qwq", sb.toString() + "-" + inetAddress.getHostName());
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return hosts;
    }

    private String IP() {
//        try {
//            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface intf : interfaces) {
//                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
//                for (InetAddress addr : addrs) {
//                    if (!addr.isLoopbackAddress()) {
//                        String sAddr = addr.getHostAddress();
//                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
//                        boolean isIPv4 = sAddr.indexOf(':')<0;
//
//                        if (true) {
//                            if (isIPv4)
//                                return sAddr;
//                        } else {
//                            if (!isIPv4) {
//                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
//                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.e("fdasd",ex.getMessage());
//        } // for now eat exceptions
//          return "";
        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, DeviceActivity.class));
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

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
