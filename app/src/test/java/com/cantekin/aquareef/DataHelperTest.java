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

        result = DataHelper.convert((byte) 22, (byte) 00);
        System.out.println(result);

        result = DataHelper.convert((byte) 10, (byte) 31);
        System.out.println(result);

        result = DataHelper.convert((byte) 4, (byte) 96);
        System.out.print(result);
        Assert.assertEquals("11:20", result);

    }
}
