package com.cantekin.aquareef;

import com.cantekin.aquareef.Data.DataHelper;
import com.cantekin.aquareef.Data.DefaultData;
import com.cantekin.aquareef.network.NetworkDevice;
import com.cantekin.aquareef.network.UdpDataService;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Cantekin on 6.8.2017.
 */

public class DataHelperTest {

    @Test
    public void ConvertTest() throws Exception {
        String result = DataHelper.convert((byte) 9, (byte) 31);
        System.out.println(result);
        Assert.assertEquals("09:31", result);

        result = DataHelper.convert((byte) 22, (byte) 00);
        System.out.println(result);
        Assert.assertEquals("22:00", result);


        result = DataHelper.convert((byte) 5, (byte) 05);
        System.out.println(result);
        Assert.assertEquals("05:05", result);

        result = DataHelper.convert((byte) 4, (byte) 96);
        System.out.print(result);
        Assert.assertEquals("04:96", result);

    }

    @Test
    public void StringToBynariTest() throws Exception {
        String start = "12:00";
        byte a = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(start.replace(":", "")));
        byte b = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(start.replace(":", "")));
        System.out.println(a);
        System.out.println(b);
//        System.out.println("------------------");
//        start = "12:00";
//        System.out.println(start);
//
//        a = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(start.replace(":", "")));
//        System.out.println(start);
//
//        b = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(start.replace(":", "")));
//        System.out.println(a);
//        System.out.println(b);
        //Assert.assertEquals("04:96", result);

    }


    @Test
    public void ByteTest() throws Exception {
        byte x = -80;
        int a = ((int) x) & 0xff;
        System.out.println(a);
        UdpDataService d = new UdpDataService();
        NetworkDevice device = new NetworkDevice();
        //System.out.println(b);
        device.setIP("192.168.0.14");
        device.setPort("8899");

        for (int i = 0; i < 5; i++)
            d.send(device, new DefaultData().getScheduleFavorites().get(0).getData().get(0).getByte());
    }
}
