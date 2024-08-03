package com.manga.mangareaderapp.model;

import java.io.Serializable;

public class Rating implements Serializable {
    private String uid;
    private String imgUser;
    private String userName;
    private String mangaName;
    private String rateValue;
    private String comment;

    public Rating() {

    }

    public Rating(String uid, String imgUser, String userName, String mangaName, String rateValue, String comment) {
        this.uid = uid;
        this.imgUser = imgUser;
        this.userName = userName;
        this.mangaName = mangaName;
        this.rateValue = rateValue;
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMangaName() {
        return mangaName;
    }

    public void setMangaName(String mangaName) {
        this.mangaName = mangaName;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
