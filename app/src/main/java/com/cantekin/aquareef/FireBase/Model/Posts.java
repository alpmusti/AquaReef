package com.cantekin.aquareef.FireBase.Model;

/**
 * Created by Cantekin on 24.7.2017.
 */

public class Posts {
    private String key;
    private String deviceID;
    private String downloadURL;
    private Long like;
    private String sharedDate;
    private String user;
    private String userNote;
    public DateAll allDate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public String getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(String sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public DateAll getAllDate() {
        return allDate;
    }

    public void setAllDate(DateAll allDate) {
        this.allDate = allDate;
    }
}
