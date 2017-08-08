package com.cantekin.aquareef;

import com.cantekin.aquareef.Data.DataHelper;

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
}
