package com.cantekin.aquareef.network;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Cantekin on 27.6.2017.
 */

public class UdpDataService implements IDataService {
    @Override
    public boolean send(NetworkDevice device, String message) {
        if (device == null || message == null)
            return false;
        //TODO data gönderme yapılacak
        sendDevice(device, message);
        return true;
    }


    @Override
    public boolean send(NetworkDevice device, byte[] message) {
        if (device == null || message == null)
            return false;
        //TODO data gönderme yapılacak
        sendDevice(device, message);
        return true;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void sendDevice(NetworkDevice device, byte[] message) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(device.getIP());
            DatagramSocket serverSocket = new DatagramSocket();
            DatagramPacket msgPacket = new DatagramPacket(message,
                    message.length, addr, Integer.parseInt(device.getPort()));
            serverSocket.send(msgPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public boolean send(NetworkDevice[] device, String message) {
        return false;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void sendDevice(NetworkDevice device, String message) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(device.getIP());
            DatagramSocket serverSocket = new DatagramSocket();
            DatagramPacket msgPacket = new DatagramPacket(message.getBytes(),
                    message.getBytes().length, addr, Integer.parseInt(device.getPort()));
            serverSocket.send(msgPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
