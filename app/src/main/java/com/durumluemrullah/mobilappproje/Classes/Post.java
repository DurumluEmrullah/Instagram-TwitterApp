package com.durumluemrullah.mobilappproje.Classes;

import android.graphics.Bitmap;

import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Post {

       private String userEmail;
       private String profilePhotoUri;
       private String postUri;
       private String explanation;
       private FieldValue date;


    public Post(){

     }

    public Post(String userEmail, String profilePhotoUri,  String postUri, String explanation) {
        this.userEmail = userEmail;
        this.profilePhotoUri = profilePhotoUri;
        this.postUri = postUri;
        this.explanation = explanation;
    }

    public Post(String userEmail, String profilePhotoUri,  String postUri, String explanation, FieldValue date) {
        this.userEmail = userEmail;
        this.profilePhotoUri = profilePhotoUri;
        this.postUri = postUri;
        this.explanation = explanation;
        this.date = date;
    }

    public FieldValue getDate() {
        return date;
    }

    public void setDate(FieldValue date) {
        this.date = date;
    }


    public String getProfilePhotoUri() {
        return profilePhotoUri;
    }

    public void setProfilePhotoUri(String profilePhotoUri) {
        this.profilePhotoUri = profilePhotoUri;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



    public String getPostUri() {
        return postUri;
    }

    public void setPostUri(String postUri) {
        this.postUri = postUri;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
