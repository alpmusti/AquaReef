package com.cantekin.aquareef.Data;

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


    public byte[] ToArrayBuffer() {
        byte[] buffer = new byte[15];
        buffer[0] = (byte) code;
        buffer[1] = (byte)(Integer.parseInt(start.replace(":",""))/256);
        buffer[2] = (byte)((Integer.parseInt(start.replace(":",""))) % 256);

        buffer[3] = (byte)((Integer.parseInt(up.replace(":","")) / 256));
        buffer[4] =(byte)((Integer.parseInt(up.replace(":","")) % 256));

        buffer[5] = (byte)((Integer.parseInt(down.replace(":","")) / 256));
        buffer[6] = (byte)(Integer.parseInt(down.replace(":","")) % 256);

        buffer[7] = (byte)((Integer.parseInt(stop.replace(":",""))) / 256);
        buffer[8] = (byte)((Integer.parseInt(stop.replace(":",""))) % 256);
        buffer[9] = 0;
        buffer[10] = (byte)level;
        buffer[11] = 0;
        buffer[12] = 0;
        buffer[13] = 0;
        buffer[14] = 0;
        return buffer;
    }
}
