package com.cantekin.aquareef.network;

/**
 * Created by Cantekin on 27.6.2017.
 */

interface IDataService {
    boolean send(NetworkDevice device, String message);
    boolean send(NetworkDevice device, byte[] message);
    boolean send(NetworkDevice[] device, String message);
}
