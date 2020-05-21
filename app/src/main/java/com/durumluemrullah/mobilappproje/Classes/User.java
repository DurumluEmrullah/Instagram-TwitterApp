package com.durumluemrullah.mobilappproje.Classes;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class User {
    @Exclude private String id;
    private String userName;
    private String userEmail;
    private String name;
    private String lastName;
    private String biography;
    private String profilePhoto;



    public User(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String userName, String name, String lastName, String biography, String profilePhoto, String userEmail) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.biography = biography;
        this.profilePhoto = profilePhoto;
        this.userEmail=userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
