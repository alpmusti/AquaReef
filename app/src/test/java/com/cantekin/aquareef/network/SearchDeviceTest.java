package com.cantekin.aquareef.network;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Cantekin on 2.7.2017.
 */
public class SearchDeviceTest {
    @Test
    public void searchDevices() throws Exception    {
        ISearchDevice search=new SearchDevice();
        List<NetworkDevice> devices=search.search();
    }
}