package com.cantekin.aquareef.Data;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Created by Cantekin on 19.7.2017.
 */

public class DataHelper {
    public static final int constValue = 40;

    public static byte ByteTranslateFirst(int value) {
        return (byte) ((value * constValue) / 256);
    }

    public static byte ByteTranslateSecond(int value) {
        return (byte) ((value * constValue) % 256);
    }

    public static byte ByteTranslateFirstNoCost(int value) {
        return (byte) (value / 256);
    }

    public static String convert(byte value1, byte value2) {
        //int val = (value1 * 256) + value2;
        String res = String.format("%02d", value1) + ":" + String.format("%02d", value2);
        //res = res.substring(0, 2) + ":" + res.substring(2, 4);
        return res;
    }

    public static byte ByteTranslateSecondNoCost(int value) {
        return (byte) (value % 256);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


}
