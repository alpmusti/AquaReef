package com.cantekin.aquareef.AquaLink;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UdpUnicast implements INetworkTransmission {

    private static final String TAG = Constants.TAG + "UdpUnicast";
    private static final int BUFFER_SIZE = 2048;

    private String ip;
    private int port = Constants.UDP_PORT;
    private DatagramSocket socket;
    private DatagramPacket packetToSend;
    private InetAddress inetAddress;
    private ReceiveData receiveData;
    private UdpUnicastListener listener;
    private byte[] buffer = new byte[BUFFER_SIZE];


    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }


    /**
     * @param listener the listener to set
     */
    public void setListener(UdpUnicastListener listener) {
        this.listener = listener;
    }


    public UdpUnicast() {
        super();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    /**
     * Open udp socket
     */
    public synchronized boolean open() {

        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }

        //receive response
        Log.e(TAG, "opennnnnn");

        receiveData = new ReceiveData();
        receiveData.start();
        return true;
    }

    /**
     * Close udp socket
     */
    public synchronized void close() {
        stopReceive();
        if (socket != null) {
            socket.close();
        }
    }

    /**
     * send message
     *
     * @param text the message to broadcast
     */
    public synchronized boolean send(String text) {

        Log.e(TAG, "send:" + text);
//        Log.e("netr adresss", inetAddress.getHostAddress());
//        Log.e("port", port + "");
        if (socket == null) {
            return false;
        }

        if (text == null) {
            return true;
        }

        packetToSend = new DatagramPacket(
                text.getBytes(), text.getBytes().length, inetAddress, port);

        //send data
        try {
            socket.send(packetToSend);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Stop to receive
     */
    public void stopReceive() {

        if (receiveData != null && !receiveData.isStoped()) {
            receiveData.stop();
        }
    }

    public interface UdpUnicastListener {
        public void onReceived(byte[] data, int length);
    }

    private class ReceiveData implements Runnable {

        private boolean stop = false;
        private Thread thread;

        private ReceiveData() {
            thread = new Thread(this);
        }

        @Override
        public void run() {

            while (!stop) {
                try {
                 //   Log.i(TAG, "runrunrunrun");
                    DatagramPacket packetToReceive = new DatagramPacket(buffer, BUFFER_SIZE);
                    socket.receive(packetToReceive);
                    onReceive(buffer, packetToReceive.getLength());
                    //  Log.d(TAG, "receievd");
                    //Log.e(TAG, buffer.toString());
                } catch (SocketTimeoutException e) {
                    Log.w(TAG, "Receive packet timeout!");
                } catch (IOException e1) {
                    Log.w(TAG, "Socket is closed!");
                }
            }
        }

        void start() {
            Log.d(TAG, "startstartstart");
            if (!thread.isAlive())
                thread.start();
        }

        void stop() {
            stop = true;
        }

        boolean isStoped() {
            return stop;
        }
    }

    @Override
    public void setParameters(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void onReceive(byte[] buffer, int length) {
        Log.e(TAG, new String(buffer, 0, length));
        if (listener != null) {
            listener.onReceived(buffer, length);
        }
    }
}
