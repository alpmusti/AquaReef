package com.cantekin.aquareef.Data;

import android.util.Log;

/**
 * Created by Cantekin on 19.7.2017.
 */

public class DataSchedule {
    private char code;
    private String name;
    private String color;
    private String start;
    private String up;
    private String down;
    private String stop;
    private int level;
    private boolean moon;
    private int blue;

    public DataSchedule(String name, char code, String color, String start, String up, String down, String stop, int level) {
        this.code = code;
        this.name = name;
        this.color = color;
        this.start = start;
        this.up = up;
        this.down = down;
        this.stop = stop;
        this.level = level;
    }

    public byte[] getByte() {
        if (code == 'h')
            return stringToArrayBufferScheduleMoon();
        else return stringToArrayBufferSchedule();
    }

    public void setProperties(byte[] buffer, int index) {

        setCode(index);
        if (code == '!')
            return;
        name = DefaultData.getNameForKey(code);
        color = DefaultData.getColorForKey(code);
        Log.d("setProperties", "code :" + name);


        if (code != 'h') {
            start = DataHelper.convert(buffer[0], buffer[1]);
            stop = DataHelper.convert(buffer[2], buffer[3]);
            up = DataHelper.convert(buffer[4], buffer[5]);
            down = DataHelper.convert(buffer[6], buffer[7]);
            level = buffer[8];

        } else {
            start = DataHelper.convert(buffer[3], buffer[4]);
            stop = DataHelper.convert(buffer[5], buffer[6]);

            level = buffer[7];
            blue = buffer[0];
            moon = buffer[1] == 1;
        }
        Log.d("setProperties", "level=" + level);

        for (int i = 0; i < buffer.length; i++) {
            Log.i("setProperties", "buffer[" + i + "]=" + buffer[i]);

        }

    }

    private void setCode(int index) {
        switch (index) {
            case 1:
                code = DefaultData.codeBlueC;
                break;
            case 10:
                code = DefaultData.codeWhiteC;
                break;
            case 19:
                code = DefaultData.codeRoyalC;
                break;
            case 28:
                code = DefaultData.codeRedC;
                break;
            case 37:
                code = DefaultData.codeGreenC;
                break;
            case 46:
                code = DefaultData.codeDWhiteC;
                break;
            case 55:
                code = DefaultData.codeUVC;
                break;
            case 64:
                code = DefaultData.codeMoonC;
                break;
            default:
                code = '!';
                break;
        }
    }

    private byte[] stringToArrayBufferSchedule() {
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        if (code != 'h') {
            buffer[1] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(start.replace(":", "")));
            buffer[2] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(start.replace(":", "")));
            buffer[3] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(up.replace(":", "")));
            buffer[4] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(up.replace(":", "")));
            buffer[5] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(down.replace(":", "")));
            buffer[6] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(down.replace(":", "")));
            buffer[7] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(stop.replace(":", "")));
            buffer[8] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(stop.replace(":", "")));
            buffer[9] = 0;
            buffer[10] = (byte) this.level;
            buffer[11] = 0;
            buffer[12] = 0;
            buffer[13] = 0;
            buffer[14] = 0;
        }
        return buffer;
    }

    private byte[] stringToArrayBufferScheduleMoon() {
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        buffer[1] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(start.replace(":", "")));
        buffer[2] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(start.replace(":", "")));
        buffer[3] = DataHelper.ByteTranslateFirstNoCost(Integer.parseInt(stop.replace(":", "")));
        buffer[4] = DataHelper.ByteTranslateSecondNoCost(Integer.parseInt(stop.replace(":", "")));
        buffer[5] = 0;
        buffer[6] = (byte) level;
        buffer[7] = 0;
        buffer[8] = (byte) blue;
        buffer[9] = 0;
        if (moon)
            buffer[10] = 1;
        else
            buffer[10] = 0;
        buffer[11] = 0;
        buffer[12] = 0;
        buffer[13] = 0;
        buffer[14] = 0;
        return buffer;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public boolean isMoon() {
        return moon;
    }

    public void setMoon(boolean moon) {
        this.moon = moon;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public DataSchedule() {
    }

    public byte[] stringToSimpleArrayBufferFavorite() {
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
//        buffer[1] = DataHelper.ByteTranslateFirst(this.red);
//        buffer[2] = DataHelper.ByteTranslateSecond(this.red);
//        buffer[3] = DataHelper.ByteTranslateFirst(this.green);
//        buffer[4] = DataHelper.ByteTranslateSecond(this.green);
//        buffer[5] = DataHelper.ByteTranslateFirst(this.royalBlue);
//        buffer[6] = DataHelper.ByteTranslateSecond(this.royalBlue);
//        buffer[7] = DataHelper.ByteTranslateFirst(this.blue);
//        buffer[8] = DataHelper.ByteTranslateSecond(this.blue);
//        buffer[9] = DataHelper.ByteTranslateFirst(this.white);
//        buffer[10] = DataHelper.ByteTranslateSecond(this.white);
//        buffer[11] = DataHelper.ByteTranslateFirst(this.dWhite);
//        buffer[12] = DataHelper.ByteTranslateSecond(this.dWhite);
//        buffer[13] = DataHelper.ByteTranslateFirst(this.uv);
//        buffer[14] = DataHelper.ByteTranslateSecond(this.uv);
        return buffer;
    }


//    public byte[] ToArrayBuffer() {
//        byte[] buffer = new byte[15];
//        buffer[0] = (byte) code;
//        buffer[1] = (byte) (Integer.parseInt(start.replace(":", "")) / 256);
//        buffer[2] = (byte) ((Integer.parseInt(start.replace(":", ""))) % 256);
//
//        buffer[3] = (byte) ((Integer.parseInt(up.replace(":", "")) / 256));
//        buffer[4] = (byte) ((Integer.parseInt(up.replace(":", "")) % 256));
//
//        buffer[5] = (byte) ((Integer.parseInt(down.replace(":", "")) / 256));
//        buffer[6] = (byte) (Integer.parseInt(down.replace(":", "")) % 256);
//
//        buffer[7] = (byte) ((Integer.parseInt(stop.replace(":", ""))) / 256);
//        buffer[8] = (byte) ((Integer.parseInt(stop.replace(":", ""))) % 256);
//        buffer[9] = 0;
//        buffer[10] = (byte) level;
//        buffer[11] = 0;
//        buffer[12] = 0;
//        buffer[13] = 0;
//        buffer[14] = 0;
//        return buffer;
//    }
}
