package com.cantekin.aquareef.Controller;

import android.database.CharArrayBuffer;

/**
 * Created by Cantekin on 19.7.2017.
 */

public class Data {
    private char code;
    private int red;
    private int green;
    private int royalBlue;
    private int blue;
    private int white;
    private int dWhite;
    private int uv;

    public Data() {
        this.red = 0;
        this.green = 0;
        this.royalBlue = 0;
        this.blue = 0;
        this.white = 0;
        this.dWhite = 0;
        this.uv = 0;
        this.code = 'm';
    }

    public byte[] stringToSimpleArrayBufferFavorite() {
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        buffer[1] = DataHelper.ByteTranslateFirst(this.red);
        buffer[2] = DataHelper.ByteTranslateSecond(this.red);
        buffer[3] = DataHelper.ByteTranslateFirst(this.green);
        buffer[4] = DataHelper.ByteTranslateSecond(this.green);
        buffer[5] = DataHelper.ByteTranslateFirst(this.royalBlue);
        buffer[6] = DataHelper.ByteTranslateSecond(this.royalBlue);
        buffer[7] = DataHelper.ByteTranslateFirst(this.blue);
        buffer[8] = DataHelper.ByteTranslateSecond(this.blue);
        buffer[9] = DataHelper.ByteTranslateFirst(this.white);
        buffer[10] = DataHelper.ByteTranslateSecond(this.white);
        buffer[11] = DataHelper.ByteTranslateFirst(this.dWhite);
        buffer[12] = DataHelper.ByteTranslateSecond(this.dWhite);
        buffer[13] = DataHelper.ByteTranslateFirst(this.uv);
        buffer[14] = DataHelper.ByteTranslateSecond(this.uv);
        return buffer;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getRoyalBlue() {
        return royalBlue;
    }

    public void setRoyalBlue(int royalBlue) {
        this.royalBlue = royalBlue;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getdWhite() {
        return dWhite;
    }

    public void setdWhite(int dWhite) {
        this.dWhite = dWhite;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }
}
