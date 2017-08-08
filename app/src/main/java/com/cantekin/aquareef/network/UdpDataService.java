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
    public void send(List<NetworkDevice> devices, final byte[] message) {
        for (final NetworkDevice device : devices) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendDevice(device, message);
                }
            }).start();
        }
    }

    @Override
    public void send(final NetworkDevice device, final byte[] message) {
        //TODO data gönderme yapılacak
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendDevice(device, message);
            }
        }).start();
    }


    @Override
    public void send(List<NetworkDevice> devices, final String message) {

        for (final NetworkDevice device : devices) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("sendData==>", "IP:" + device.getIP() + " Port:" + device.getPort() + " Message:" + message);
                    sendDevice(device, message.getBytes());
                }
            }).start();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void sendDevice(NetworkDevice device, byte[] message) {
        InetAddress addr = null;
        DatagramSocket serverSocket = null;
        try {
            addr = InetAddress.getByName(device.getIP());
            serverSocket = new DatagramSocket();
            //serverSocket.setSoTimeout(1000);
            DatagramPacket msgPacket = new DatagramPacket(message,
                    message.length, addr, Integer.parseInt(device.getPort()));
            serverSocket.send(msgPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public byte[] receive(List<NetworkDevice> device) {
        InetAddress addr = null;
        byte[] messageReceive = new byte[1500];
        byte[] message = new String("zzzzzzzzzzzzzzz").getBytes();
        DatagramSocket serverSocket = null;
        try {
            Log.i("OKUMA", "data gönderiliyor");
            Log.i("OKUMA", "data zzzzzzzzzzzzzzz");
            addr = InetAddress.getByName(device.get(0).getIP());
            serverSocket = new DatagramSocket();
            DatagramPacket msgPacket = new DatagramPacket(message,
                    message.length, addr, Integer.parseInt(device.get(0).getPort()));
            serverSocket.send(msgPacket);
            msgPacket = new DatagramPacket(messageReceive, messageReceive.length);
            serverSocket.setSoTimeout(1000);
            serverSocket.receive(msgPacket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
        }
        return messageReceive;
    }


}
