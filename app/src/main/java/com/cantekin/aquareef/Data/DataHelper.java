package com.cantekin.aquareef.Data;

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
}
