package com.cantekin.aquareef.network;

import java.util.List;

/**
 * Created by Cantekin on 27.6.2017.
 */

interface IDataService {
    void send(NetworkDevice device, byte[] message);
    void send(List<NetworkDevice> device, byte[] message);
    void send(NetworkDevice device, String message);
    void send(List<NetworkDevice>  device, String message);
}
