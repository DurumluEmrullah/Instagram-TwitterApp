package com.durumluemrullah.mobilappproje.Classes;

import android.graphics.Bitmap;

import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;

public class Twit {


    private String userId;
    private String userName;
    private String userEmail;
    private String Twit;
    private String twitId;
    private String profilePhoto;
    private FieldValue shareTime;

    public Twit(){

    }

    public Twit(String userId, String userName, String userEmail, String twit, String twitId, String profilePhoto) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        Twit = twit;
        this.twitId = twitId;
        this.profilePhoto = profilePhoto;
    }

    public Twit(String userId, String userName, String userEmail, String twit, String twitId, String profilePhoto, FieldValue shareTime) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        Twit = twit;
        this.twitId = twitId;
        this.profilePhoto = profilePhoto;
        this.shareTime = shareTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTwit() {
        return Twit;
    }

    public void setTwit(String twit) {
        Twit = twit;
    }

    public String getTwitId() {
        return twitId;
    }

    public void setTwitId(String twitId) {
        this.twitId = twitId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public FieldValue getShareTime() {
        return shareTime;
    }

    public void setShareTime(FieldValue shareTime) {
        this.shareTime = shareTime;
    }
}
