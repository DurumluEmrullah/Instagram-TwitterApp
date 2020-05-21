package com.durumluemrullah.mobilappproje.Classes;

import com.google.firebase.firestore.FieldValue;

public class MessageLobi {

    private String senderEmail;
    private String senderProfilePhoto;
    private String receiverEmail;
    private String receiverProfilePhoto;
    private String lobiId;
    private FieldValue date;

    public MessageLobi(String senderEmail, String senderProfilePhoto, String lobiId) {
        this.senderEmail = senderEmail;
        this.senderProfilePhoto = senderProfilePhoto;
        this.lobiId = lobiId;
    }

    public MessageLobi(String senderEmail, String senderProfilePhoto, String receiverEmail, String receiverProfilePhoto, String lobiId, FieldValue date) {
        this.senderEmail = senderEmail;
        this.senderProfilePhoto=senderProfilePhoto;
        this.receiverEmail = receiverEmail;
        this.receiverProfilePhoto=receiverProfilePhoto;
        this.lobiId = lobiId;
        this.date=date;
    }

    public FieldValue getDate() {
        return date;
    }

    public void setDate(FieldValue date) {
        this.date = date;
    }

    public String getReceiverProfilePhoto() {
        return receiverProfilePhoto;
    }

    public void setReceiverProfilePhoto(String receiverProfilePhoto) {
        this.receiverProfilePhoto = receiverProfilePhoto;
    }

    public String getSenderProfilePhoto() {
        return senderProfilePhoto;
    }

    public void setSenderProfilePhoto(String senderProfilePhoto) {
        this.senderProfilePhoto = senderProfilePhoto;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getLobiId() {
        return lobiId;
    }

    public void setLobiId(String lobiId) {
        this.lobiId = lobiId;
    }


}
