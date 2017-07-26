package com.cantekin.aquareef.network;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Cantekin on 27.6.2017.
 */

public class UdpDataService implements IDataService {

    @Override
    public void send(List<NetworkDevice> devices, byte[] message) {
        for (NetworkDevice device : devices) {
            sendDevice(device, message);
        }
    }

    @Override
    public void send(NetworkDevice device, byte[] message) {
        //TODO data gönderme yapılacak
        sendDevice(device, message);
    }

    @Override
    public void send(NetworkDevice device, String message) {
        Log.i("sendData==>", "IP:" + device.getIP() + " Port:" + device.getPort() + " Message:" + message);

        sendDevice(device, message.getBytes());
    }

    @Override
    public void send(List<NetworkDevice> devices, String message) {
        for (NetworkDevice device : devices) {
            Log.i("sendData==>", "IP:" + device.getIP() + " Port:" + device.getPort() + " Message:" + message);

            sendDevice(device, message.getBytes());
        }
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


//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void sendDevice(NetworkDevice device, String message) {
//        InetAddress addr = null;
//        try {
//            addr = InetAddress.getByName(device.getIP());
//            DatagramSocket serverSocket = new DatagramSocket();
//            DatagramPacket msgPacket = new DatagramPacket(message.getBytes(),
//                    message.getBytes().length, addr, Integer.parseInt(device.getPort()));
//            serverSocket.send(msgPacket);
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
}
