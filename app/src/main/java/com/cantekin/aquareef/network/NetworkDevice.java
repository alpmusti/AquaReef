package com.cantekin.aquareef.network;

/**
 * herbir akvaryum için tanımlanmalı
 * Created by Cantekin on 27.6.2017.
 */

public class NetworkDevice {

    private String IP;
    private String Port;
    private String Mac;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }
}
