package com.cantekin.aquareef.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Cantekin on 2.7.2017.
 */

public class SearchDevice implements ISearchDevice {
    @Override
    public List<NetworkDevice> search() {
        System.out.println("ARAMAMAA");

        List<NetworkDevice> deviceList = new ArrayList<>();
        NetworkDevice device;
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
                device = new NetworkDevice();
                device.setMac(sb.toString());
                device.setPort("8899");
                while (ias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ias.nextElement();
                    System.out.println(ia.getAddress());
                    device.setIP(ia.getAddress().toString());
                }

            }
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());

        }
        return deviceList;
    }
}
