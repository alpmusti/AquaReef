package com.cantekin.aquareef.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * cihazla ve mesajı alarak gönderim işlemini yapacak
 * cihaza erişimi kontrol edecek
 *
 * Created by Cantekin on 27.6.2017.
 */

public class DataServiceTest {
    IDataService service=new UdpDataService();
    @Test
    public void sendData()  throws Exception{
      //  Assert.assertTrue(service.send(new NetworkDevice(),"message"));
    }

    @Test
    public void sendData_withNullMessage()  throws Exception{
        //Assert.assertFalse(service.send(new NetworkDevice(),null));
    }
}
